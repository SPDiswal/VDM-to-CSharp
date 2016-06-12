package org.overture.codegen.vdm2cs.parser

import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.common.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.lambda.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.linq.CsQueryExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.primary.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.ternary.CsTernaryCondition
import org.overture.codegen.vdm2cs.parser.ast.expressions.unary.*
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*
import org.overture.codegen.vdm2cs.parser.ast.statements.*
import org.overture.codegen.vdm2cs.utilities.*
import kotlin.reflect.KClass

object CsFormatter
{
    fun format(csAst: CsNode) = csAst.format

    private val CsNode.format: String
        get() = when (this)
        {

        //region COMMON

            is CsDocument                        ->
            {
                val formattedSymbolDefinitions = symbolDefinitions.format.joinIfNotEmpty("\n", postfix = "\n\n")
                val formattedUsingDirectives = usingDirectives.format.joinIfNotEmpty("\n", postfix = "\n\n")
                "$formattedSymbolDefinitions$formattedUsingDirectives${namespace.format}"
            }

            is CsSymbolDefinition                -> "#define ${symbol.format}"

            is CsUsingDirective                  -> when (isStatic)
            {
                false -> "using ${importedType.format};"
                true  -> "using static ${importedType.format};"
            }

        //endregion

        //region IDENTIFIERS

            is CsCommonIdentifier                -> name

            is CsVerbatimIdentifier              -> "@$name"

            is CsQualifiedNameIdentifier         -> (qualifiers.format + identifier.format).joinToString(".")

            is CsTypeNameIdentifier              -> typeName.format

            is CsQualifiedTypeNameIdentifier     -> type.format

            is CsGenericTypeIdentifier           -> "${typeName.format}<${typeArguments.formatIdentifierList}>"

            is CsNullableTypeIdentifier          -> "${type.format}?"

        //endregion

        //region DECLARATIONS

            is CsNamespaceDeclaration            -> "namespace ${name.format}${members.formatMembers}"

            is CsClassDeclaration                ->
            {
                val formattedSuperTypes = superTypes.format.joinIfNotEmpty(", ", prefix = " : ")

                "${attributes.formatAttributes}" +
                "${modifiers.formatModifiers}" +
                "class ${name.format}" +
                "${typeParameters.formatTypeParameters}" +
                "$formattedSuperTypes" +
                "${members.formatMembers}"
            }

            is CsMethodDeclaration               ->
            {
                val formattedAttributes = attributes.formatAttributes
                val formattedName = name?.format?.let { " $it" } ?: ""
                val formattedReturnType = returnType.format
                val signature = "${modifiers.formatModifiers}" +
                                "$formattedReturnType$formattedName" +
                                "${typeParameters.formatTypeParameters}" +
                                "${parameters.formatParameters}"

                val formattedStatement = when
                {
                    statement !is CsBlockStatement -> " => ${statement.format.alignAt(("$signature => ").length)}"
                    statement.isEmptyBlock         -> " ${statement.format}"
                    else                           ->
                    {
                        val partition = statement.statements.partition { it.isContractDirective }
                        val (contractDirectives, statements) = partition

                        val formattedStatements = when
                        {
                            statements.isEmpty()            -> contractDirectives.formatIntoBlock()
                            statements.isRedundantBlockOnly ->
                            {
                                val uncovered = statements.single().uncoverBlocks
                                when (uncovered)
                                {
                                    is CsBlockStatement -> (contractDirectives to uncovered.statements)
                                    else                -> (contractDirectives to listOf(uncovered))
                                }.formatIntoBlock(partitionSeparator = "\n\n")
                            }
                            else                            -> partition.formatIntoBlock(partitionSeparator = "\n\n")
                        }

                        "\n$formattedStatements"
                    }
                }

                "$formattedAttributes$signature$formattedStatement"
            }

            is CsPropertyDeclaration             ->
            {
                val formattedInitialiser = if (initialiser != null) " = ${initialiser.format};" else ""

                "${attributes.formatAttributes}" +
                "${modifiers.formatModifiers}" +
                "${type.format} ${name.format} " +
                "{ ${listOf(getter?.format, setter?.format).filterNotNull().joinToString(" ")} }" +
                "$formattedInitialiser"
            }

            is CsPropertyAutoGetter              -> "${modifiers.formatModifiers}get;"

            is CsPropertyAutoSetter              -> "${modifiers.formatModifiers}set;"

            is CsEnumDeclaration                 -> "${attributes.formatAttributes}" +
                                                    "${modifiers.formatModifiers}" +
                                                    "enum ${name.format}" +
                                                    "${members.formatEnumMembers}"

            is CsEnumConstantDeclaration         -> when (value)
            {
                null -> "${name.format}"
                else -> "${name.format} = ${value.format}"
            }

        //endregion

        //region LAMBDA EXPRESSIONS

            is CsExpressionLambdaExpression      ->
            {
                val formattedParameters = parameters.formatParameters
                "$formattedParameters => ${expression.format.alignAt("$formattedParameters => ".length)}"
            }

            is CsStatementLambdaExpression       -> "${parameters.formatParameters} =>\n${statement.format}"

        //endregion

        //region QUERY EXPRESSIONS

            is CsQueryExpression                 ->
            {
                val (fromClauses, whereClause, selectClause) = this

                (fromClauses.map { "from ${it.first.format} in ${it.second.format}" } +
                 whereClause?.let { "where ${it.format}" } +
                 selectClause.let { "select ${it.format}" }).filterNotNull().joinToString("\n")
            }

        //endregion

        //region TERNARY OPERATOR EXPRESSIONS

            is CsTernaryCondition                ->
            {
                val formattedCondition = when
                {
                    condition is CsIsExpression &&
                    condition.type !is CsNullableTypeIdentifier -> "(${condition.formatRightAssociatedIn(this)})"
                    else                                        -> condition.formatRightAssociatedIn(this)
                }

                val formattedThen = thenExpression.formatRightAssociatedIn(this)
                val formattedElse = elseExpression.formatParenthesisedIn(this)

                "$formattedCondition ? $formattedThen : $formattedElse"
            }

        //endregion

        //region BINARY OPERATOR EXPRESSIONS

            is CsOrExpression                    -> formatCommutativeOperatorExpression("||")

            is CsXOrExpression                   -> formatCommutativeOperatorExpression("^")

            is CsAndExpression                   -> formatCommutativeOperatorExpression("&&")

            is CsEqualToExpression               -> formatLeftAssociativeOperatorExpression("==")

            is CsNotEqualToExpression            -> formatLeftAssociativeOperatorExpression("!=")

            is CsGreaterThanExpression           -> formatLeftAssociativeOperatorExpression(">")

            is CsGreaterThanOrEqualToExpression  -> formatLeftAssociativeOperatorExpression(">=")

            is CsLessThanExpression              -> formatLeftAssociativeOperatorExpression("<")

            is CsLessThanOrEqualToExpression     -> formatLeftAssociativeOperatorExpression("<=")

            is CsPlusExpression                  -> formatCommutativeOperatorExpression("+")

            is CsMinusExpression                 -> formatLeftAssociativeOperatorExpression("-")

            is CsTimesExpression                 -> formatCommutativeOperatorExpression("*")

            is CsDivideExpression                -> formatLeftAssociativeOperatorExpression("/")

            is CsModulusExpression               -> formatLeftAssociativeOperatorExpression("%")

        //endregion

        //region UNARY OPERATOR EXPRESSIONS

            is CsUnaryPlusExpression             -> formatUnaryOperatorExpression("+")

            is CsUnaryMinusExpression            -> formatUnaryOperatorExpression("-")

            is CsNotExpression                   -> formatUnaryOperatorExpression("!")

        //endregion

        //region TYPE EXPRESSIONS

            is CsIsExpression                    -> "${expression.formatParenthesisedIn(this)} is ${type.format}"

            is CsSafeTypeCastExpression          -> "${expression.formatParenthesisedIn(this)} as ${type.format}"

            is CsTypeCastExpression              -> "(${type.format}) ${expression.format}"

        //endregion

        //region LITERAL EXPRESSIONS

            is CsBooleanExpression               -> "$value"

            is CsDecimalExpression               -> "$integralDigits.${fractionalDigits}m"

            is CsIntegerExpression               -> digits

            is CsCharacterExpression             -> "'$character'"

            is CsStringExpression                -> "\"$contents\""

            is CsNullExpression                  -> "null"

        //endregion

        //region PRIMARY EXPRESSIONS

            is CsNameIdentifierExpression        -> "${identifier.format}"

            is CsTypeIdentifierExpression        -> "${typeIdentifier.format}"

            is CsMemberExpression                -> "${receiver.formatParenthesisedIn(this)}.${member.format}"

            is CsIndexerExpression               -> "${receiver.formatParenthesisedIn(this)}" +
                                                    "[${arguments.formatArguments}]"

            is CsCallExpression                  -> "${receiver.format}" +
                                                    "${typeArguments.formatTypeArguments}" +
                                                    "(${arguments.formatArguments})"

            is CsNewExpression                   ->
            {
                val formattedType = type?.let { " ${it.format}" } ?: ""
                val formattedArguments = arguments?.let { "(${it.formatArguments})" } ?: ""
                val formattedInitialiser = initialiser?.let { " ${it.format}" } ?: ""
                "new$formattedType$formattedArguments$formattedInitialiser"
            }

            is CsArrayExpression                 ->
            {
                val formattedType = type?.let { " ${it.format}" } ?: ""
                val formattedInitialiser = initialiser?.let { " ${it.format}" } ?: ""
                "new$formattedType[${arguments.formatArguments}]$formattedInitialiser"
            }

            is CsAnonymousInitialiserExpression  ->
            {
                "{ ${fields.map {
                    val (identifier, expression) = it
                    val formattedExpression = expression?.let { " = ${it.format}" } ?: ""
                    "${identifier.format}$formattedExpression"
                }.joinToString(", ", postfix = " ")}}"
            }

            is CsCollectionInitialiserExpression -> "{ ${items.map { it.format }.joinIfNotEmpty(", ", postfix = " ")}}"

            is CsDictionaryInitialiserExpression ->
            {
                "{ ${items.map {
                    val (indexer, expression) = it
                    "[${indexer.formatArguments}] = ${expression.format}"
                }.joinIfNotEmpty(", ", postfix = " ")}}"
            }

            is CsThisExpression                  -> "this"

        //endregion

        //region STATEMENTS

            is CsBlockStatement                  -> when
            {
                isEmptyBlock     -> "{ }"
                isRedundantBlock -> uncoverBlocks.format
                else             -> statements.filterNot { it.isEmptyBlock }.formatIntoBlock()
            }

            is CsExpressionStatement             -> "${expression.format};"

            is CsVariableDeclarationStatement    -> when
            {
                type == null        ->
                {
                    val formattedReceiver = "var ${identifier.format} = "
                    "$formattedReceiver${initialiser!!.format.alignAt(formattedReceiver.length)};"
                }
                initialiser == null -> "${type.format} ${identifier.format};"
                else                ->
                {
                    val formattedReceiver = "${type.format} ${identifier.format} = "
                    "$formattedReceiver${initialiser.format.alignAt(formattedReceiver.length)};"
                }
            }

            is CsAssignmentStatement             ->
            {
                val formattedReceiver = "${receiver.format} = "
                "$formattedReceiver${expression.format.alignAt(formattedReceiver.length)};"
            }

            is CsIfStatement                     ->
            {
                val formattedThen = when
                {
                    thenStatement.isSimpleBlock        -> " ${thenStatement.uncoverBlocks.format}"
                    thenStatement.isEmptyBlock ||
                    thenStatement !is CsBlockStatement -> " ${thenStatement.format}"
                    else                               -> "\n${thenStatement.format}"
                }

                when (elseStatement)
                {
                    null -> "if (${condition.format.alignAt("if (".length)})$formattedThen"
                    else ->
                    {
                        val formattedElse = when
                        {
                            elseStatement.isNestedIf ||
                            elseStatement.isSimpleBlock        -> " ${elseStatement.uncoverBlocks.format}"
                            elseStatement.isEmptyBlock ||
                            elseStatement !is CsBlockStatement -> " ${elseStatement.format}"
                            else                               -> "\n${elseStatement.format}"
                        }

                        "if (${condition.format.alignAt("if (".length)})$formattedThen\nelse$formattedElse"
                    }
                }
            }

            is CsReturnStatement                 -> "return ${expression.format.alignAt("return ".length)};"

            is CsThrowStatement                  -> "throw ${exception.format};"

        //endregion

            else                                 -> throw IllegalStateException(this.javaClass.simpleName)
        }

    private val precedences = listOf(
        setOf(CsUnaryPlusExpression::class, CsUnaryMinusExpression::class, CsNotExpression::class,
              CsTypeCastExpression::class),
        setOf(CsTimesExpression::class, CsDivideExpression::class, CsModulusExpression::class),
        setOf(CsPlusExpression::class, CsMinusExpression::class),
        setOf(CsGreaterThanExpression::class, CsGreaterThanOrEqualToExpression::class,
              CsLessThanExpression::class, CsLessThanOrEqualToExpression::class,
              CsIsExpression::class, CsSafeTypeCastExpression::class),
        setOf(CsEqualToExpression::class, CsNotEqualToExpression::class),
        setOf(CsAndExpression::class),
        setOf(CsXOrExpression::class),
        setOf(CsOrExpression::class),
        setOf(CsTernaryCondition::class),
        setOf(CsQueryExpression::class)
    )

    private fun CsExpression.formatParenthesisedIn(parent: CsExpression,
                                                   predicate: (Int, Int) -> Boolean = { child, parent -> child > parent }): String
    {
        val childPrecedence = precedences.indexOfFirst { it.contains<KClass<*>>(this.javaClass.kotlin) }
        val parentPrecedence = precedences.indexOfFirst { it.contains<KClass<*>>(parent.javaClass.kotlin) }
        return if (predicate(childPrecedence, parentPrecedence)) "(${this.format.alignAt(1)})" else this.format
    }

    private fun CsExpression.formatRightAssociatedIn(parent: CsExpression): String
        = this.formatParenthesisedIn(parent) { child, parent -> child >= parent }

    private fun CsBinaryOperatorExpression.formatCommutativeOperatorExpression(infixOperator: String)
        = "${left.formatParenthesisedIn(this)} " +
          "$infixOperator " +
          "${right.formatParenthesisedIn(this)}"

    private fun CsBinaryOperatorExpression.formatLeftAssociativeOperatorExpression(infixOperator: String)
        = "${left.formatParenthesisedIn(this)} " +
          "$infixOperator " +
          "${right.formatRightAssociatedIn(this)}"

    private fun CsUnaryOperatorExpression.formatUnaryOperatorExpression(prefixOperator: String)
        = "$prefixOperator${expression.formatRightAssociatedIn(this)}"

    private val List<CsNode>.format: List<String>
        get() = map { it.format }

    private val List<CsNode>.formatIndented: List<String>
        get() = format.map { it.prependIndent("    ") }

    private fun List<CsNode>.formatIntoBlock(separator: String = "\n", prefix: String = "", postfix: String = "")
        = "$prefix{\n${formatIndented.joinIfNotEmpty(separator, postfix = "\n")}}$postfix"

    private fun Pair<List<CsNode>, List<CsNode>>.formatIntoBlock(partitionSeparator: String = "\n",
                                                                 firstSeparator: String = "\n",
                                                                 secondSeparator: String = "\n",
                                                                 prefix: String = "",
                                                                 postfix: String = ""): String
    {
        val (first, second) = this
        val firstIndentedContents = first.formatIndented.joinIfNotEmpty(firstSeparator, postfix = partitionSeparator)
        val secondIndentedContents = second.formatIndented.joinIfNotEmpty(secondSeparator, postfix = "\n")
        return "$prefix{\n$firstIndentedContents$secondIndentedContents}$postfix"
    }

    private val List<CsDeclaration>.formatMembers: String
        get() = formatIntoBlock(separator = "\n\n", prefix = "\n")

    private val List<CsDeclaration>.formatEnumMembers: String
        get() = formatIntoBlock(separator = ",\n", prefix = "\n")

    private val List<CsExpression>.formatArguments: String
        get() = format.joinToString(", ")

    private val List<CsExpression>.formatAttributes: String
        get() = map { "[${it.format}]" }.joinIfNotEmpty("\n", postfix = "\n")

    private val List<CsModifier>.formatModifiers: String
        get() = map { it.name.toLowerCase() }.joinIfNotEmpty(" ", postfix = " ")

    private val List<CsIdentifier>.formatIdentifierList: String
        get() = format.joinToString(", ")

    private val List<CsNameIdentifier>?.formatTypeParameters: String
        get() = if (this != null) "<$formatIdentifierList>" else ""

    private val List<CsTypeIdentifier>?.formatTypeArguments: String
        get() = if (this != null) "<$formatIdentifierList>" else ""

    private val List<Pair<CsNameIdentifier, CsTypeIdentifier?>>.formatParameters: String
        get() = if (size == 1 && first().second == null)
            first().first.format
        else
            "(${map { ("${it.second?.format ?: ""} ${it.first.format}").trimStart() }.joinToString(", ")})"

    private fun <T> List<T>.joinIfNotEmpty(separator: String,
                                           prefix: String = "",
                                           postfix: String = "",
                                           ifEmpty: String = "")
        = if (isNotEmpty()) joinToString(separator, prefix, postfix) else ifEmpty
}
