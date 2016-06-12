package org.overture.codegen.vdm2cs.parser

import me.sargunvohra.lib.cakeparse.api.*
import me.sargunvohra.lib.cakeparse.exception.*
import me.sargunvohra.lib.cakeparse.lexer.*
import me.sargunvohra.lib.cakeparse.parser.Parser
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
import org.overture.codegen.vdm2cs.parser.tokens.*
import org.overture.codegen.vdm2cs.parser.tokens.CsIdentifierTokenDefinition.IDENTIFIER
import org.overture.codegen.vdm2cs.parser.tokens.CsLiteralTokenDefinition.*
import org.overture.codegen.vdm2cs.parser.tokens.CsPunctuationTokenDefinition.*
import org.overture.codegen.vdm2cs.parser.tokens.CsReservedWordTokenDefinition.*

object CsParser
{
    fun parse(input: String) = parse(input, rule = document)
    
    fun <TNode : CsNode> parse(input: String, rule: Parser<TNode>): TNode
    {
        try
        {
            return CsScanner.scan(input).parseToEnd(rule).value
        }
        catch(e: UnexpectedTokenException)
        {
            throw ParseException("Parsing failed in '$input': ${e.message}")
        }
    }
    
    val reservedWords: Set<String> by lazy {
        CsReservedWordTokenDefinition.values().map { it.pattern }.toSet()
    }

    //region COMMON

    fun <TContents> codeBlockOf(target: Parser<TContents>) = (LEFT_BRACE then target before RIGHT_BRACE)

    val document: Parser<CsNode> by lazy {
        zeroOrMore(symbolDefinition) and
            zeroOrMore(usingDirective or usingStaticDirective) and
            namespaceDeclaration mapTriple
            {
                val (symbolDefinitions, usingDirectives, namespaceDeclaration) = it
                CsDocument(symbolDefinitions, usingDirectives, namespaceDeclaration)
            }
    }

    val symbolDefinition: Parser<CsSymbolDefinition> by lazy {
        DEFINE then unqualifiedNameIdentifier map { CsSymbolDefinition(it) }
    }

    val usingDirective: Parser<CsUsingDirective> by lazy {
        (USING then qualifiedNameIdentifier map {
            CsUsingDirective(CsQualifiedTypeNameIdentifier(it), isStatic = false)
        }) before SEMICOLON
    }

    val usingStaticDirective: Parser<CsUsingDirective> by lazy {
        (USING then STATIC then qualifiedNameIdentifier map {
            CsUsingDirective(CsQualifiedTypeNameIdentifier(it), isStatic = true)
        }) before SEMICOLON
    }

    val modifierList: Parser<List<CsModifier>> by lazy {
        zeroOrMore((IMPLICIT map { CsModifier.IMPLICIT }) or
                       (OPERATOR map { CsModifier.OPERATOR }) or
                       (OVERRIDE map { CsModifier.OVERRIDE }) or
                       (PARTIAL map { CsModifier.PARTIAL}) or
                       (PRIVATE map { CsModifier.PRIVATE }) or
                       (PUBLIC map { CsModifier.PUBLIC }) or
                       (SEALED map { CsModifier.SEALED }) or
                       (STATIC map { CsModifier.STATIC }))
    }

    val typeNamePair: Parser<Pair<CsTypeIdentifier, CsNameIdentifier>> by lazy {
        typeIdentifier and unqualifiedNameIdentifier
    }

    val nonEmptyNameList: Parser<List<CsNameIdentifier>> by lazy {
        oneOrMoreSeparatedBy(ref { unqualifiedNameIdentifier }, COMMA)
    }

    val typeList: Parser<List<CsTypeIdentifier>> by lazy {
        zeroOrMoreSeparatedBy(ref { typeIdentifier }, COMMA)
    }

    val nonEmptyTypeList: Parser<List<CsTypeIdentifier>> by lazy {
        oneOrMoreSeparatedBy(ref { typeIdentifier }, COMMA)
    }

    val typeArgumentList: Parser<List<CsTypeIdentifier>> by lazy {
        LESS_THAN then typeList before GREATER_THAN
    }

    val typeParameterList: Parser<List<CsNameIdentifier>> by lazy {
        LESS_THAN then nonEmptyNameList before GREATER_THAN
    }

    val parameterList: Parser<List<Pair<CsNameIdentifier, CsTypeIdentifier>>> by lazy {
        LEFT_PARENTHESIS then
            zeroOrMoreSeparatedBy(typeNamePair, COMMA) before
            RIGHT_PARENTHESIS map { it.map { it.second to it.first } }
    }

    val expressionList: Parser<List<CsExpression>> by lazy {
        zeroOrMoreSeparatedBy(ref { expression }, COMMA)
    }

    val indexedExpressionList: Parser<List<Pair<List<CsExpression>, CsExpression>>> by lazy {
        zeroOrMoreSeparatedBy(bracketedArgumentList and (ASSIGNMENT then ref { expression }), COMMA)
    }

    val argumentList: Parser<List<CsExpression>> by lazy {
        LEFT_PARENTHESIS then expressionList before RIGHT_PARENTHESIS
    }

    val bracketedArgumentList: Parser<List<CsExpression>> by lazy {
        LEFT_BRACKET then expressionList before RIGHT_BRACKET
    }

    val bracedArgumentList: Parser<List<CsExpression>> by lazy {
        LEFT_BRACE then expressionList before RIGHT_BRACE
    }

    val attribute: Parser<CsExpression> by lazy {
        LEFT_BRACKET then ref { expression } before RIGHT_BRACKET
    }

    val attributeList: Parser<List<CsExpression>> by lazy {
        zeroOrMore(attribute)
    }

    //endregion

    //region IDENTIFIERS

    val commonIdentifier: Parser<CsCommonIdentifier> by lazy {
        IDENTIFIER map { CsCommonIdentifier(it.raw) }
    }

    val verbatimIdentifier: Parser<CsVerbatimIdentifier> by lazy {
        VERBATIM then oneOf(CsReservedWordTokenDefinition.tokenDefinitions + IDENTIFIER) map
            { CsVerbatimIdentifier(it.raw) }
    }

    val unqualifiedNameIdentifier: Parser<CsNameIdentifier> by lazy {
        verbatimIdentifier or commonIdentifier
    }

    val qualifiedNameIdentifier: Parser<CsQualifiedNameIdentifier> by lazy {
        oneOrMoreSeparatedBy(unqualifiedNameIdentifier, DOT) map {
            CsQualifiedNameIdentifier(it.dropLast(1), it.last())
        }
    }

    val typeNameIdentifier: Parser<CsTypeNameIdentifier> by lazy {
        oneOf(BOOL, BYTE, CHAR, DECIMAL,
              DOUBLE, DYNAMIC, FLOAT, INT,
              LONG, OBJECT, SBYTE, SHORT,
              STRING, UINT, ULONG, USHORT, VOID) map { CsTypeNameIdentifier(CsCommonIdentifier(it.raw)) }
    }

    val qualifiedTypeNameIdentifier: Parser<CsQualifiedTypeNameIdentifier> by lazy {
        qualifiedNameIdentifier map { CsQualifiedTypeNameIdentifier(it) }
    }

    val genericTypeIdentifier: Parser<CsGenericTypeIdentifier> by lazy {
        qualifiedTypeNameIdentifier and typeArgumentList map
            {
                val (qualifiedTypeName, typeArguments) = it
                CsGenericTypeIdentifier(qualifiedTypeName, typeArguments)
            }
    }

    val nullableTypeIdentifier: Parser<CsNullableTypeIdentifier> by lazy {
        (genericTypeIdentifier or
            qualifiedTypeNameIdentifier or
            typeNameIdentifier) before QUESTION_MARK map { CsNullableTypeIdentifier(it) }
    }

    val typeIdentifier: Parser<CsTypeIdentifier> by lazy {
        nullableTypeIdentifier or
            genericTypeIdentifier or
            qualifiedTypeNameIdentifier or
            typeNameIdentifier
    }

    //endregion

    //region DECLARATIONS

    val namespaceDeclaration: Parser<CsNamespaceDeclaration> by lazy {
        (NAMESPACE then qualifiedNameIdentifier) and                                // Qualified namespace name.
            codeBlockOf(zeroOrMore(namespaceMember)) map                            // Members.
            {
                val (namespaceIdentifier, members) = it
                CsNamespaceDeclaration(namespaceIdentifier, members)
            }
    }

    val namespaceMember: Parser<CsDeclaration> by lazy {
        ref { namespaceDeclaration } or
            ref { classDeclaration } or
            ref { enumDeclaration }
    }

    val classDeclaration: Parser<CsClassDeclaration> by lazy {
        attributeList and                                                           // Attributes.
            (modifierList before CLASS) and                                         // Modifiers.
            unqualifiedNameIdentifier and                                           // Class name.
            optional(typeParameterList) and                                         // Type parameters.
            optional(COLON then nonEmptyTypeList) and                               // Supertypes.
            codeBlockOf(zeroOrMore(classMember)) mapSextuple                        // Members.
            {
                val (attributes, modifiers, classIdentifier, typeParameters, superTypes, members) = it
                CsClassDeclaration(classIdentifier, typeParameters, superTypes ?: emptyList(),
                                   modifiers, attributes, members)
            }
    }

    val classMember: Parser<CsDeclaration> by lazy {
        ref { classDeclaration } or ref { methodDeclaration } or ref { propertyDeclaration }
    }

    val methodDeclaration: Parser<CsMethodDeclaration> by lazy {
        attributeList and                                                           // Attributes.
            modifierList and                                                        // Modifiers.
            typeIdentifier and                                                      // Return type.
            optional(qualifiedNameIdentifier) and                                   // Qualified method name.
            optional(typeParameterList) and                                         // Type parameters.
            parameterList and                                                       // Parameters.
            (blockStatement or                                                      // Block body.
                (GOES_TO then expressionBodyStatement)) mapSeptuple                 // Expression-body.
            {
                val (attributes, modifiers, returnType, methodIdentifier, typeParameters, parameters, statement) = it

                CsMethodDeclaration(methodIdentifier, returnType, typeParameters, parameters,
                                    modifiers, attributes, statement)
            }
    }

    val propertyDeclaration: Parser<CsPropertyDeclaration> by lazy {
        attributeList and                                                           // Attributes.
            modifierList and                                                        // Modifiers.
            typeIdentifier and                                                      // Type.
            qualifiedNameIdentifier and                                             // Qualified property name.
            codeBlockOf(optional(propertyGetter) and optional(propertySetter)) and  // Getter and setter.
            optional(EQUAL_TO then ref { expression }) mapSextuple                  // Initialiser.
            {
                val (attributes, modifiers, type, propertyIdentifier, getterAndSetter, initialiser) = it
                val (getter, setter) = getterAndSetter

                CsPropertyDeclaration(propertyIdentifier, type, getter, setter, initialiser, modifiers, attributes)
            }
    }

    val propertyGetter: Parser<CsPropertyGetter> by lazy { propertyAutoGetter }

    val propertyAutoGetter: Parser<CsPropertyAutoGetter> by lazy {
        modifierList before GET before SEMICOLON map { CsPropertyAutoGetter(it) }
    }

    val propertySetter: Parser<CsPropertySetter> by lazy { propertyAutoSetter }

    val propertyAutoSetter: Parser<CsPropertyAutoSetter> by lazy {
        modifierList before SET before SEMICOLON map { CsPropertyAutoSetter(it) }
    }

    val enumDeclaration: Parser<CsEnumDeclaration> by lazy {
        attributeList and                                                                   // Attributes.
            (modifierList before ENUM) and                                                  // Modifiers.
            unqualifiedNameIdentifier and                                                   // Enum name.
            codeBlockOf(zeroOrMoreSeparatedBy(enumConstantDeclaration, COMMA)) mapQuadruple // Members.
            {
                val (attributes, modifiers, enumIdentifier, members) = it
                CsEnumDeclaration(enumIdentifier, modifiers, attributes, members)
            }
    }

    val enumConstantDeclaration: Parser<CsEnumConstantDeclaration> by lazy {
        unqualifiedNameIdentifier and                                                       // Name.
            optional(ASSIGNMENT then ref { expression }) map                                // Value.
            {
                val (identifier, value) = it
                CsEnumConstantDeclaration(identifier, value)
            }
    }

    //endregion

    //region EXPRESSIONS

    val expression: Parser<CsExpression> by lazy {
        lambdaExpression or
            queryExpression or
            ternaryConditionalExpression
    }

    //region LAMBDA EXPRESSIONS

    val lambdaExpression: Parser<CsExpression> by lazy {
        statementLambdaExpression or
            expressionLambdaExpression
    }

    val expressionLambdaExpression: Parser<CsExpression> by lazy {
        (lambdaParameters before GOES_TO) and ref { expression } map
            {
                val (parameters, expression) = it
                CsExpressionLambdaExpression(parameters, expression)
            }
    }

    val statementLambdaExpression: Parser<CsExpression> by lazy {
        (lambdaParameters before GOES_TO) and blockStatement map
            {
                val (parameters, statement) = it
                CsStatementLambdaExpression(parameters, statement)
            }
    }

    val lambdaParameters: Parser<List<Pair<CsNameIdentifier, CsTypeIdentifier?>>> by lazy {
        singleImplicitlyTypedLambdaParameter or
            implicitlyTypedLambdaParameters or
            parameterList
    }

    val singleImplicitlyTypedLambdaParameter: Parser<List<Pair<CsNameIdentifier, CsTypeIdentifier?>>> by lazy {
        unqualifiedNameIdentifier map { listOf(it to null) }
    }

    val implicitlyTypedLambdaParameters: Parser<List<Pair<CsNameIdentifier, CsTypeIdentifier?>>> by lazy {
        LEFT_PARENTHESIS then
            zeroOrMoreSeparatedBy(unqualifiedNameIdentifier, COMMA) before
            RIGHT_PARENTHESIS map { it.map { it to null } }
    }

    //endregion

    //region QUERY EXPRESSIONS

    val queryExpression: Parser<CsQueryExpression> by lazy {
        oneOrMore((FROM then unqualifiedNameIdentifier) and (IN then ref { expression })) and
            optional(WHERE then ref { expression }) and
            (SELECT then ref { expression }) mapTriple
            {
                val (fromClauses, whereClause, selectClause) = it
                CsQueryExpression(fromClauses, whereClause, selectClause)
            }
    }

    //endregion

    //region TERNARY EXPRESSIONS

    val ternaryConditionalExpression: Parser<CsExpression> by lazy {
        orExpression and
            optional((QUESTION_MARK then ref { expression }) and (COLON then ref { expression })) map
            {
                val (condition, consequenceExpressions) = it

                if (consequenceExpressions != null)
                {
                    val (thenExpression, elseExpression) = consequenceExpressions
                    CsTernaryCondition(condition, thenExpression, elseExpression)
                }
                else
                    condition
            }
    }

    //endregion

    //region BINARY EXPRESSIONS

    val binaryOperators = mapOf<Token, (CsExpression, CsExpression) -> CsExpression>(
        OR.token to { left, right -> CsOrExpression(left, right) },
        XOR.token to { left, right -> CsXOrExpression(left, right) },
        AND.token to { left, right -> CsAndExpression(left, right) },
        EQUAL_TO.token to { left, right -> CsEqualToExpression(left, right) },
        NOT_EQUAL_TO.token to { left, right -> CsNotEqualToExpression(left, right) },
        LESS_THAN.token to { left, right -> CsLessThanExpression(left, right) },
        LESS_THAN_OR_EQUAL_TO.token to { left, right -> CsLessThanOrEqualToExpression(left, right) },
        GREATER_THAN.token to { left, right -> CsGreaterThanExpression(left, right) },
        GREATER_THAN_OR_EQUAL_TO.token to { left, right -> CsGreaterThanOrEqualToExpression(left, right) },
        PLUS.token to { left, right -> CsPlusExpression(left, right) },
        MINUS.token to { left, right -> CsMinusExpression(left, right) },
        TIMES.token to { left, right -> CsTimesExpression(left, right) },
        DIVIDE.token to { left: CsExpression, right: CsExpression -> CsDivideExpression(left, right) },
        MODULUS.token to { left: CsExpression, right: CsExpression -> CsModulusExpression(left, right) }
    )

    val equalityOperator = oneOf(EQUAL_TO, NOT_EQUAL_TO)
    val relationalOperator = oneOf(LESS_THAN, LESS_THAN_OR_EQUAL_TO, GREATER_THAN, GREATER_THAN_OR_EQUAL_TO)
    val additiveOperator = oneOf(PLUS, MINUS)
    val multiplicativeOperator = oneOf(TIMES, DIVIDE, MODULUS)

    val orExpression: Parser<CsExpression> by lazy {
        leftAssociate(xOrExpression and zeroOrMore(OR and xOrExpression))
    }

    val xOrExpression: Parser<CsExpression> by lazy {
        leftAssociate(andExpression and zeroOrMore(XOR and andExpression))
    }

    val andExpression: Parser<CsExpression> by lazy {
        leftAssociate(equalityExpression and zeroOrMore(AND and equalityExpression))
    }

    val equalityExpression: Parser<CsExpression> by lazy {
        leftAssociate(relationalExpression and zeroOrMore(equalityOperator and relationalExpression))
    }

    val relationalExpression: Parser<CsExpression> by lazy {
        typeExpression or leftAssociate(additiveExpression and zeroOrMore(relationalOperator and additiveExpression))
    }

    val typeExpression: Parser<CsExpression> by lazy {
        isExpression or safeTypeCastExpression
    }

    val isExpression: Parser<CsIsExpression> by lazy {
        (additiveExpression and (IS then typeIdentifier)) map
            {
                val (expression, type) = it
                CsIsExpression(expression, type)
            }
    }

    val safeTypeCastExpression: Parser<CsSafeTypeCastExpression> by lazy {
        (additiveExpression and (AS then typeIdentifier)) map
            {
                val (expression, type) = it
                CsSafeTypeCastExpression(expression, type)
            }
    }

    val additiveExpression: Parser<CsExpression> by lazy {
        leftAssociate(multiplicativeExpression and zeroOrMore(additiveOperator and multiplicativeExpression))
    }

    val multiplicativeExpression: Parser<CsExpression> by lazy {
        leftAssociate(unaryExpression and zeroOrMore(multiplicativeOperator and unaryExpression))
    }

    private fun leftAssociate(target: Parser<Pair<CsExpression, List<Pair<TokenInstance, CsExpression>>>>)
        = target map
        {
            val (headExpression, tail) = it
            var result = headExpression

            for ((operator, expression) in tail)
            {
                val operation = binaryOperators[operator.type]
                if (operation != null) result = operation(result, expression)
            }

            result
        }

    //endregion

    //region UNARY EXPRESSIONS

    val unaryExpression: Parser<CsExpression> by lazy {
        typeCastExpression or
            notExpression or
            unaryMinusExpression or
            unaryPlusExpression or
            primaryExpression
    }

    val typeCastExpression: Parser<CsTypeCastExpression> by lazy {
        (LEFT_PARENTHESIS then typeIdentifier before RIGHT_PARENTHESIS) and primaryExpression map
            {
                val (type, expression) = it
                CsTypeCastExpression(type, expression)
            }
    }

    val notExpression: Parser<CsNotExpression> by lazy {
        EXCLAMATION_MARK then ref { unaryExpression } map { CsNotExpression(it) }
    }

    val unaryMinusExpression: Parser<CsUnaryMinusExpression> by lazy {
        MINUS then ref { unaryExpression } map { CsUnaryMinusExpression(it) }
    }

    val unaryPlusExpression: Parser<CsUnaryPlusExpression> by lazy {
        PLUS then ref { unaryExpression } map { CsUnaryPlusExpression(it) }
    }

    //endregion

    //region PRIMARY EXPRESSIONS

    val primaryExpression: Parser<CsExpression> by lazy {
        atomicExpression and zeroOrMore(trailingFeature) map
            {
                val (receiver, trailingFeatures) = it
                val features = listOf(LValueFeature.RootLValue(receiver)) + trailingFeatures
                constructLValue(features)
            }
    }

    private val trailingFeature: Parser<LValueFeature> by lazy {
        memberExpression or
            indexerExpression or
            callExpression
    }

    private val memberExpression: Parser<LValueFeature.MemberLValue> by lazy {
        DOT then unqualifiedNameIdentifier map { LValueFeature.MemberLValue(it) }
    }

    private val indexerExpression: Parser<LValueFeature.IndexerLValue> by lazy {
        bracketedArgumentList map { LValueFeature.IndexerLValue(it) }
    }

    private val callExpression: Parser<LValueFeature.CallLValue> by lazy {
        optional(typeArgumentList) and argumentList map
            {
                val (typeArguments, arguments) = it
                LValueFeature.CallLValue(typeArguments, arguments)
            }
    }

    private sealed class LValueFeature
    {
        class RootLValue(val rootExpression: CsExpression) : LValueFeature()

        class MemberLValue(val member: CsNameIdentifier) : LValueFeature()

        class IndexerLValue(val arguments: List<CsExpression>) : LValueFeature()

        class CallLValue(val typeArguments: List<CsTypeIdentifier>?,
                         val arguments: List<CsExpression>) : LValueFeature()
    }

    private fun constructLValue(features: List<LValueFeature>): CsExpression
    {
        val last = features.last()
        val remainingFeatures = features.dropLast(1)

        return when (last)
        {
            is LValueFeature.RootLValue    -> last.rootExpression

            is LValueFeature.MemberLValue  -> CsMemberExpression(constructLValue(remainingFeatures),
                                                                 last.member)

            is LValueFeature.IndexerLValue -> CsIndexerExpression(constructLValue(remainingFeatures),
                                                                  last.arguments)

            is LValueFeature.CallLValue    -> CsCallExpression(constructLValue(remainingFeatures),
                                                               last.typeArguments,
                                                               last.arguments)
        }
    }

    //endregion

    //region ATOMIC EXPRESSIONS

    val atomicExpression: Parser<CsExpression> by lazy {
        nameIdentifierExpression or
            typeIdentifierExpression or
            newAnonymousExpression or
            newExpression or
            arrayExpression or
            thisExpression or
            literalExpression or
            parenthesisedExpression
    }

    val nameIdentifierExpression: Parser<CsNameIdentifierExpression> by lazy {
        unqualifiedNameIdentifier map { CsNameIdentifierExpression(it) }
    }

    val typeIdentifierExpression: Parser<CsTypeIdentifierExpression> by lazy {
        typeIdentifier map { CsTypeIdentifierExpression(it) }
    }

    val newExpression: Parser<CsNewExpression> by lazy {
        (NEW then typeIdentifier) and
            ((argumentList and optional(initialiserExpression)) or
                (optional(argumentList) and initialiserExpression)) map
            {
                val (typeIdentifier, argumentsAndInitialiser) = it
                val (arguments, initialiser) = argumentsAndInitialiser
                CsNewExpression(typeIdentifier, arguments, initialiser)
            }
    }

    val newAnonymousExpression: Parser<CsNewExpression> by lazy {
        NEW then anonymousInitialiserExpression map { CsNewExpression(null, null, it) }
    }

    val arrayExpression: Parser<CsArrayExpression> by lazy {
        (NEW then optional(typeIdentifier)) and bracketedArgumentList and
            optional(collectionInitialiserExpression) mapTriple
            {
                val (identifier, arguments, initialiser) = it
                CsArrayExpression(identifier, arguments, initialiser)
            }
    }

    val thisExpression: Parser<CsThisExpression> by lazy {
        THIS map { CsThisExpression }
    }

    //endregion

    //region INITIALISERS

    val initialiserExpression: Parser<CsInitialiserExpression> by lazy {
        collectionInitialiserExpression or
            dictionaryInitialiserExpression
    }

    val nameOptionalExpressionPair: Parser<Pair<CsNameIdentifier, CsExpression?>> by lazy {
        unqualifiedNameIdentifier and optional(ASSIGNMENT then ref { expression })
    }

    val anonymousInitialiserExpression: Parser<CsAnonymousInitialiserExpression> by lazy {
        LEFT_BRACE then oneOrMoreSeparatedBy(nameOptionalExpressionPair, COMMA) before RIGHT_BRACE map {
            CsAnonymousInitialiserExpression(it)
        }
    }

    val collectionInitialiserExpression: Parser<CsCollectionInitialiserExpression> by lazy {
        bracedArgumentList map { CsCollectionInitialiserExpression(it) }
    }

    val dictionaryInitialiserExpression: Parser<CsDictionaryInitialiserExpression> by lazy {
        LEFT_BRACE then indexedExpressionList before RIGHT_BRACE map {
            CsDictionaryInitialiserExpression(it)
        }
    }

    //endregion

    //region LITERALS

    val literalExpression: Parser<CsExpression> by lazy {
        nullExpression or
            booleanExpression or
            decimalExpression or
            integerExpression or
            characterExpression or
            stringExpression
    }

    val nullExpression: Parser<CsNullExpression> by lazy {
        NULL map { CsNullExpression }
    }

    val booleanExpression: Parser<CsBooleanExpression> by lazy {
        (TRUE map { CsBooleanExpression(true) }) or
            (FALSE map { CsBooleanExpression(false) })
    }

    val decimalExpression: Parser<CsDecimalExpression> by lazy {
        DECIMAL_LITERAL.value map
            {
                val numberParts = it.removeSuffix("m").split(".")
                val integralDigits = numberParts[0]
                val fractionalDigits = when
                {
                    it.contains(".") && numberParts[1] != "" -> numberParts[1]
                    else                                     -> "0"
                }

                CsDecimalExpression(integralDigits, fractionalDigits)
            }
    }

    val integerExpression: Parser<CsIntegerExpression> by lazy {
        INTEGER_LITERAL.value map { CsIntegerExpression(it) }
    }

    val characterExpression: Parser<CsCharacterExpression> by lazy {
        CHARACTER_LITERAL.value map { CsCharacterExpression(it.substring(1 .. it.length - 2)) }
    }

    val stringExpression: Parser<CsStringExpression> by lazy {
        STRING_LITERAL.value map { CsStringExpression(it.substring(1 .. it.length - 2)) }
    }

    //endregion

    val parenthesisedExpression: Parser<CsExpression> by lazy {
        LEFT_PARENTHESIS then ref { expression } before RIGHT_PARENTHESIS
    }

    //endregion

    //region STATEMENTS

    val statement: Parser<CsStatement> by lazy {
        blockStatement or
            expressionBodyStatement or
            variableDeclarationStatement or
            assignmentStatement or
            ifStatement or
            returnStatement or
            throwStatement
    }

    val blockStatement: Parser<CsBlockStatement> by lazy {
        codeBlockOf(zeroOrMore(ref { statement })) map { CsBlockStatement(it) }
    }

    val expressionBodyStatement: Parser<CsStatement> by lazy {
        expressionStatement or
            assignmentStatement
    }

    val expressionStatement: Parser<CsExpressionStatement> by lazy {
        ref { expression } before SEMICOLON map { CsExpressionStatement(it) }
    }

    val variableDeclarationStatement: Parser<CsVariableDeclarationStatement> by lazy {
        explicitlyTypedVariableDeclarationStatement or
            implicitlyTypedVariableDeclarationStatement
    }

    val explicitlyTypedVariableDeclarationStatement: Parser<CsVariableDeclarationStatement> by lazy {
        typeIdentifier and unqualifiedNameIdentifier and
            optional(ASSIGNMENT then ref { expression }) before SEMICOLON mapTriple
            {
                val (type, identifier, initialiser) = it
                CsVariableDeclarationStatement(identifier, type, initialiser)
            }
    }

    val implicitlyTypedVariableDeclarationStatement: Parser<CsVariableDeclarationStatement> by lazy {
        VAR then unqualifiedNameIdentifier and (ASSIGNMENT then ref { expression }) before SEMICOLON map
            {
                val (identifier, initialiser) = it
                CsVariableDeclarationStatement(identifier, null, initialiser)
            }
    }

    val assignmentStatement: Parser<CsAssignmentStatement> by lazy {
        ref { primaryExpression } and (ASSIGNMENT then ref { expression } before SEMICOLON) map {
            val (receiver, expression) = it
            CsAssignmentStatement(receiver, expression)
        }
    }

    val ifStatement: Parser<CsIfStatement> by lazy {
        (IF then LEFT_PARENTHESIS then ref { expression } before RIGHT_PARENTHESIS) and
            ref { statement } and optional(ELSE then ref { statement }) mapTriple
            {
                val (condition, thenStatement, elseStatement) = it
                CsIfStatement(condition, thenStatement, elseStatement)
            }
    }

    val returnStatement: Parser<CsReturnStatement> by lazy {
        RETURN then ref { expression } before SEMICOLON map { CsReturnStatement(it) }
    }

    val throwStatement: Parser<CsThrowStatement> by lazy {
        THROW then ref { expression } before SEMICOLON map { CsThrowStatement(it) }
    }

    //endregion
}
