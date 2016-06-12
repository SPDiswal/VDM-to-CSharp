package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.AFormalParamLocalParamIR
import org.overture.codegen.ir.name.ATypeNameIR
import org.overture.codegen.ir.patterns.AIdentifierPatternIR
import org.overture.codegen.ir.statements.*
import org.overture.codegen.ir.types.ATemplateTypeIR
import org.overture.codegen.vdm2cs.common.DefaultNames.FLAT_SPECIFICATION_CLASS
import org.overture.codegen.vdm2cs.common.DefaultNames.PRE_STATE_PREFIX
import org.overture.codegen.vdm2cs.common.DefaultNames.RESULT_NAME
import org.overture.codegen.vdm2cs.common.Remark.*
import org.overture.codegen.vdm2cs.parser.*
import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.linq.CsQueryExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier
import org.overture.codegen.vdm2cs.parser.ast.statements.*
import org.overture.codegen.vdm2cs.translations.bindings.*
import org.overture.codegen.vdm2cs.translations.expressions.Expressions
import org.overture.codegen.vdm2cs.translations.statements.Statements
import org.overture.codegen.vdm2cs.translations.types.Types

fun parseSymbolDefinition(symbol: String)
    = CsParser.parse(symbol, rule = CsParser.symbolDefinition)

fun parseUsingDirective(importedType: String)
    = CsParser.parse(importedType, rule = CsParser.usingDirective)

fun parseUsingStaticDirective(importedType: String)
    = CsParser.parse(importedType, rule = CsParser.usingStaticDirective)

fun parseName(csName: String)
    = CsParser.parse(csName, rule = CsParser.unqualifiedNameIdentifier)

fun parseQualifiedName(csQualifiedName: String)
    = CsParser.parse(csQualifiedName, rule = CsParser.qualifiedNameIdentifier)

fun parseType(csTypeIdentifier: String)
    = CsParser.parse(csTypeIdentifier, rule = CsParser.typeIdentifier)

fun parseExpression(csExpression: String)
    = CsParser.parse(csExpression, rule = CsParser.expression)

fun parseQueryExpression(csQueryExpression: String)
    = CsParser.parse(csQueryExpression, rule = CsParser.queryExpression)

fun parseBlock(csBlockStatement: String)
    = CsParser.parse(csBlockStatement, rule = CsParser.blockStatement)

fun parseStatement(csStatement: String)
    = CsParser.parse(csStatement, rule = CsParser.statement)

fun parseModifier(csModifier: String)
    = CsModifier.valueOf(csModifier.toUpperCase())

val CsNode.format: String
    get() = CsFormatter.format(this)

val CsModifier.format: String
    get() = this.name.toLowerCase()

val SExpIR.translate: CsExpression
    get() = Expressions.translate(this)

val SExpIR.inline: String
    get() = this.translate.format

val SStmIR.translate: CsStatement
    get() = Statements.translate(this)

val SStmIR.inline: String
    get() = this.translate.format

val STypeIR.translate: CsTypeIdentifier
    get() = Types.translate(this)

val STypeIR.inline: String
    get() = this.translate.format

val SBindIR.translate: CsQueryExpression
    get() = SingleBindings.translate(this)

val SMultipleBindIR.translate: CsQueryExpression
    get() = MultipleBindings.translate(this)

val ATypeNameIR.formatTypeName: String
    get()
    {
        val enclosingSpecification = this.enclosingSpecification
        val definingSpecification = definingClass
        val name = name.toUpperCamelCase()

        val qualifier = when
        {
            enclosingSpecification?.isFlat ?: false &&
            enclosingSpecification?.name == definingSpecification -> null
            enclosingSpecification?.name != definingSpecification -> definingSpecification?.toUpperCamelCase()
            else                                                  -> null
        }

        return when (qualifier)
        {
            null -> name
            else -> "$qualifier.$name"
        }
    }

val STypeIR.promoteNamedType: String
    get() = namedInvType.name.formatTypeName.toUpperCamelCase()

fun CsClassDeclaration.toPrivate() = this.copy(modifiers = listOf(PRIVATE) +
                                                           modifiers.filter { it != PUBLIC })

fun CsMethodDeclaration.toPrivate() = this.copy(modifiers = listOf(PRIVATE) +
                                                            modifiers.filter { it != PUBLIC })

fun CsPropertyDeclaration.toPrivate() = this.copy(modifiers = listOf(PRIVATE) +
                                                              modifiers.filter { it != PUBLIC })

val List<SExpIR>.joinExpressions: String
    get() = this.map { it.inline }.joinToString(",")

val List<STypeIR>.joinTypes: String
    get() = this.map { it.inline }.joinToString(",")

val SPatternIR.inline: String
    get() = when (this)
    {
        is AIdentifierPatternIR -> when
        {
            this.hasRemark(RESULT)    -> RESULT_NAME
            this.hasRemark(PRE_STATE) -> "$PRE_STATE_PREFIX${this.name.toUpperCamelCase()}"
            else                      -> this.name.toLowerCamelCase()
        }
        else                    -> "_"
    }

fun SMultipleBindIR.translateWithPredicate(predicate: SExpIR?)
    = this.translate.copy(wherePredicate = predicate?.translate)

val List<AFormalParamLocalParamIR>.translateParameters: List<Pair<String, String>>
    get() = this.map { it.pattern.inline to it.type.inline }

val List<ATemplateTypeIR>.translateTypeParameters: List<String>?
    get() = if (this.isEmpty()) null else this.map { it.inline }

val SStmIR.translateAsBlockStatement: CsBlockStatement
    get() = CsBlockStatement(when (this)
                             {
                                 is ABlockStmIR -> this.statements.map { it.translate }
                                 is ASkipStmIR  -> emptyList()
                                 else           -> listOf(this.translate)
                             })

fun ifThen(ifCondition: String, thenStatement: String) = parseStatement("if ($ifCondition) $thenStatement")

fun expressionBody(expression: String) = parseStatement("$expression;")

fun returns(expression: String) = parseStatement("return $expression;")

fun throwsNotImplementedException() = parseStatement("throw new NotImplementedException();")

fun assigns(target: String, expression: String) = parseStatement("$target = $expression;")

fun requires(predicate: String) = parseStatement("Contract.Requires($predicate);")

fun ensures(predicate: String) = parseStatement("Contract.Ensures($predicate);")

fun invariant(predicate: String) = parseStatement("Contract.Invariant($predicate);")

fun result(returnType: String) = "Contract.Result<$returnType>()"

fun oldValue(expression: String) = "Contract.OldValue($expression)"

fun forAll(enumerable: String, predicate: String) =
    if (enumerable.startsWith("_"))
        forAll(enumerable, "_${enumerable.takeWhile { it == '_' }}", predicate)
    else
        forAll(enumerable, "_", predicate)

fun forAll(enumerable: String, itemName: String, predicate: String)
    = "Contract.ForAll($enumerable, $itemName => $predicate)"

val String.withEscapeSequences: String
    get() = this.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t")
