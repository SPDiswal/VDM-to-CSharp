package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.AVarDeclIR
import org.overture.codegen.ir.expressions.*
import org.overture.codegen.ir.name.ATypeNameIR
import org.overture.codegen.ir.patterns.ASetBindIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.ir.utils.AHeaderLetBeStIR

//region IDENTIFIERS

fun identifier(name: String, type: STypeIR) = AIdentifierVarExpIR().apply {
    this.isLambda = false
    this.isLocal = true
    this.name = name
    this.type = type.clone()
}

fun stateFieldIdentifier(name: String, type: STypeIR) = AIdentifierVarExpIR().apply {
    this.isLambda = false
    this.isLocal = false
    this.name = name
    this.type = type.clone()
}

fun qualifiedIdentifier(moduleQualifier: String, name: String, type: STypeIR) = AExplicitVarExpIR().apply {
    this.isLambda = false
    this.isLocal = false
    this.name = name
    this.type = type.clone()
    this.classType = classType(moduleQualifier)
}

//endregion

//region LITERALS

val Boolean.toLiteral: ABoolLiteralExpIR
    get() = ABoolLiteralExpIR().apply { this.type = boolType; this.value = this@toLiteral }

val Int.toLiteral: AIntLiteralExpIR
    get() = AIntLiteralExpIR().apply { this.type = intType; this.value = this@toLiteral.toLong() }

val Double.toLiteral: ARealLiteralExpIR
    get() = ARealLiteralExpIR().apply { this.type = realType; this.value = this@toLiteral }

val Char.toLiteral: ACharLiteralExpIR
    get() = ACharLiteralExpIR().apply { this.type = charType; this.value = this@toLiteral }

val String.toLiteral: AStringLiteralExpIR
    get() = AStringLiteralExpIR().apply { this.type = stringType; this.value = this@toLiteral; this.isNull = false }

fun quote(name: String) = AQuoteLiteralExpIR().apply { this.type = quoteType(name); this.value = name }

fun nil(type: STypeIR) = ANullExpIR().apply { this.type = type.clone() }

//endregion

//region ARITHMETIC

infix fun SExpIR.plus(addend: SExpIR) = APlusNumericBinaryExpIR().apply {
    this.left = this@plus.clone()
    this.right = addend.clone()
    this.type = this@plus.type.clone()
}

infix fun SExpIR.minus(subtrahend: SExpIR) = ASubtractNumericBinaryExpIR().apply {
    this.left = this@minus.clone()
    this.right = subtrahend.clone()
    this.type = this@minus.type.clone()
}

infix fun SExpIR.times(factor: SExpIR) = ATimesNumericBinaryExpIR().apply {
    this.left = this@times.clone()
    this.right = factor.clone()
    this.type = this@times.type.clone()
}

infix fun SExpIR.over(denominator: SExpIR) = ADivideNumericBinaryExpIR().apply {
    this.left = this@over.clone()
    this.right = denominator.clone()
    this.type = this@over.type.clone()
}

infix fun SExpIR.div(denominator: SExpIR) = AIntDivNumericBinaryExpIR().apply {
    this.left = this@div.clone()
    this.right = denominator.clone()
    this.type = this@div.type.clone()
}

infix fun SExpIR.mod(denominator: SExpIR) = AModNumericBinaryExpIR().apply {
    this.left = this@mod.clone()
    this.right = denominator.clone()
    this.type = this@mod.type.clone()
}

infix fun SExpIR.rem(denominator: SExpIR) = ARemNumericBinaryExpIR().apply {
    this.left = this@rem.clone()
    this.right = denominator.clone()
    this.type = this@rem.type.clone()
}

infix fun SExpIR.raisedTo(exponent: SExpIR) = APowerNumericBinaryExpIR().apply {
    this.left = this@raisedTo.clone()
    this.right = exponent.clone()
    this.type = this@raisedTo.type.clone()
}

fun unaryPlus(expression: SExpIR) = APlusUnaryExpIR().apply {
    this.type = expression.type.clone()
    this.exp = expression.clone()
}

fun unaryMinus(expression: SExpIR) = AMinusUnaryExpIR().apply {
    this.type = expression.type.clone()
    this.exp = expression.clone()
}

fun abs(expression: SExpIR) = AAbsUnaryExpIR().apply {
    this.type = expression.type.clone()
    this.exp = expression.clone()
}

fun floor(expression: SExpIR) = AFloorUnaryExpIR().apply {
    this.type = intType
    this.exp = expression.clone()
}

//endregion

//region LOGICAL

fun ternaryIf(condition: SExpIR, thenExpression: SExpIR, elseExpression: SExpIR) = ATernaryIfExpIR().apply {
    this.condition = condition.clone()
    this.trueValue = thenExpression.clone()
    this.falseValue = elseExpression.clone()
    this.type = thenExpression.type.clone()
}

infix fun SExpIR.and(conjunct: SExpIR) = AAndBoolBinaryExpIR().apply {
    this.left = this@and.clone()
    this.right = conjunct.clone()
    this.type = boolType
}

infix fun SExpIR.or(disjunct: SExpIR) = AOrBoolBinaryExpIR().apply {
    this.left = this@or.clone()
    this.right = disjunct.clone()
    this.type = boolType
}

infix fun SExpIR.xor(disjunct: SExpIR) = AXorBoolBinaryExpIR().apply {
    this.left = this@xor.clone()
    this.right = disjunct.clone()
    this.type = boolType
}

infix fun SExpIR.isEqualTo(rightOperand: SExpIR) = AEqualsBinaryExpIR().apply {
    this.left = this@isEqualTo.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isNotEqualTo(rightOperand: SExpIR) = ANotEqualsBinaryExpIR().apply {
    this.left = this@isNotEqualTo.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isGreaterThan(rightOperand: SExpIR) = AGreaterNumericBinaryExpIR().apply {
    this.left = this@isGreaterThan.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isGreaterThanOrEqualTo(rightOperand: SExpIR) = AGreaterEqualNumericBinaryExpIR().apply {
    this.left = this@isGreaterThanOrEqualTo.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isLessThan(rightOperand: SExpIR) = ALessNumericBinaryExpIR().apply {
    this.left = this@isLessThan.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isLessThanOrEqualTo(rightOperand: SExpIR) = ALessEqualNumericBinaryExpIR().apply {
    this.left = this@isLessThanOrEqualTo.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

fun not(expression: SExpIR) = ANotUnaryExpIR().apply {
    this.type = boolType
    this.exp = expression.clone()
}

//endregion

//region SETS

fun emptySetEnumeration(itemType: STypeIR) = AEnumSetExpIR().apply { this.type = setOf(itemType.clone()) }

fun setEnumeration(vararg items: SExpIR) = AEnumSetExpIR().apply {
    this.members.addAll(items.map { it.clone() })
    this.type = setOf(items.first().type.clone())
}

fun setRange(lowerBoundInclusive: SExpIR, upperBoundInclusive: SExpIR) = ARangeSetExpIR().apply {
    this.first = lowerBoundInclusive.clone()
    this.last = upperBoundInclusive.clone()
    this.type = setOf(intType)
}

fun setComprehension(bindings: List<SMultipleBindIR>, predicate: SExpIR? = null, projection: SExpIR)
    = ACompSetExpIR().apply {
    this.bindings.addAll(bindings.map { it.clone() })
    this.predicate = predicate?.clone()
    this.first = projection.clone()
    this.type = setOf(projection.type.clone())
}

infix fun SExpIR.union(rightOperand: SExpIR) = ASetUnionBinaryExpIR().apply {
    this.left = this@union.clone()
    this.right = rightOperand.clone()
    this.type = this@union.type.clone()
}

infix fun SExpIR.intersect(rightOperand: SExpIR) = ASetIntersectBinaryExpIR().apply {
    this.left = this@intersect.clone()
    this.right = rightOperand.clone()
    this.type = this@intersect.type.clone()
}

infix fun SExpIR.except(rightOperand: SExpIR) = ASetDifferenceBinaryExpIR().apply {
    this.left = this@except.clone()
    this.right = rightOperand.clone()
    this.type = this@except.type.clone()
}

infix fun SExpIR.isSubsetOf(rightOperand: SExpIR) = ASetSubsetBinaryExpIR().apply {
    this.left = this@isSubsetOf.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isProperSubsetOf(rightOperand: SExpIR) = ASetProperSubsetBinaryExpIR().apply {
    this.left = this@isProperSubsetOf.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

infix fun SExpIR.isMemberOf(rightOperand: SExpIR) = AInSetBinaryExpIR().apply {
    this.left = this@isMemberOf.clone()
    this.right = rightOperand.clone()
    this.type = boolType
}

fun card(expression: SExpIR) = ACardUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = natType
}

fun distributedUnion(expression: SExpIR) = ADistUnionUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SSetTypeIR).setOf.clone()
}

fun distributedIntersection(expression: SExpIR) = ADistIntersectUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SSetTypeIR).setOf.clone()
}

fun powerSet(expression: SExpIR) = APowerSetUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = setOf(expression.type.clone())
}

//endregion

//region SEQUENCES

fun emptySequenceEnumeration(itemType: STypeIR) = AEnumSeqExpIR().apply { this.type = seqOf(itemType.clone()) }

fun sequenceEnumeration(vararg items: SExpIR) = AEnumSeqExpIR().apply {
    this.members.addAll(items.map { it.clone() })
    this.type = seqOf(items.first().type.clone())
}

fun sequenceComprehension(binding: ASetBindIR, predicate: SExpIR? = null, projection: SExpIR) = ACompSeqExpIR().apply {
    this.setBind = binding.clone()
    this.set = binding.set.clone()
    this.predicate = predicate?.clone()
    this.first = projection.clone()
    this.type = seqOf(projection.type.clone())
}

fun subSequence(sequence: SExpIR, lowerIndexInclusive: SExpIR, upperIndexInclusive: SExpIR) = ASubSeqExpIR().apply {
    this.seq = sequence.clone()
    this.from = lowerIndexInclusive.clone()
    this.to = upperIndexInclusive.clone()
    this.type = sequence.type.clone()
}

infix fun SExpIR.concatenate(rightOperand: SExpIR) = ASeqConcatBinaryExpIR().apply {
    this.left = this@concatenate.clone()
    this.right = rightOperand.clone()
    this.type = this@concatenate.type.clone()
}

fun length(expression: SExpIR) = ALenUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = natType
}

fun elements(expression: SExpIR) = AElemsUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = setOf((expression.type as SSeqTypeIR).seqOf.clone())
}

fun indices(expression: SExpIR) = AIndicesUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = setOf(nat1Type)
}

fun head(expression: SExpIR) = AHeadUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SSeqTypeIR).seqOf.clone()
}

fun tail(expression: SExpIR) = ATailUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = expression.type.clone()
}

fun reverse(expression: SExpIR) = AReverseUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = expression.type.clone()
}

fun distributedConcatenation(expression: SExpIR) = ADistConcatUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SSeqTypeIR).seqOf.clone()
}

//endregion

//region MAPPINGS

fun emptyMappingEnumeration(from: STypeIR, to: STypeIR) = AEnumMapExpIR().apply {
    this.type = map(from.clone() to to.clone())
}

fun mappingEnumeration(vararg items: Pair<SExpIR, SExpIR>) = AEnumMapExpIR().apply {
    this.members.addAll(items.map { maplet(it.first.clone(), it.second.clone()) })
    this.type = map(items.first().first.type.clone() to items.first().second.type.clone())
}

fun mappingComprehension(bindings: List<SMultipleBindIR>, predicate: SExpIR? = null,
                         projection: AMapletExpIR) = ACompMapExpIR().apply {
    this.bindings.addAll(bindings.map { it.clone() })
    this.predicate = predicate?.clone()
    this.first = projection.clone()
    this.type = projection.type.clone()
}

fun maplet(from: SExpIR, to: SExpIR) = AMapletExpIR().apply {
    this.left = from.clone()
    this.right = to.clone()
    this.type = map(from.type.clone() to to.type.clone())
}

infix fun SExpIR.merge(rightOperand: SExpIR) = AMapUnionBinaryExpIR().apply {
    this.left = this@merge.clone()
    this.right = rightOperand.clone()
    this.type = this@merge.type.clone()
}

infix fun SExpIR.override(rightOperand: SExpIR) = AMapOverrideBinaryExpIR().apply {
    this.left = this@override.clone()
    this.right = rightOperand.clone()
    this.type = this@override.type.clone()
}

fun domain(expression: SExpIR) = AMapDomainUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SMapTypeIR).from.clone()
}

fun range(expression: SExpIR) = AMapRangeUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SMapTypeIR).from.clone()
}

fun distributedMerge(expression: SExpIR) = ADistMergeUnaryExpIR().apply {
    this.exp = expression.clone()
    this.type = (expression.type as SSetTypeIR).setOf.clone()
}

//endregion

//region TUPLES

fun mkTuple(vararg items: SExpIR) = ATupleExpIR().apply {
    this.args.addAll(items.map { it.clone() })
    this.type = tupleOf(*items.map { it.type.clone() }.toTypedArray())
}

//endregion

//region RECORDS

fun mkRecord(name: ATypeNameIR, fields: List<SExpIR>) = ANewExpIR().apply {
    this.name = name.clone()
    this.args.addAll(fields.map { it.clone() })
    this.type = recordType(name.definingClass, name.name)
}

//endregion

//region TOKENS

fun mkToken(expression: SExpIR) = AMkBasicExpIR().apply {
    this.arg = expression.clone()
    this.type = tokenType
}

//endregion

//region PRIMARY EXPRESSIONS

infix fun SExpIR.dot(member: SVarExpIR) = AFieldExpIR().apply {
    this.`object` = this@dot.clone()
    this.memberName = member.name
    this.type = member.type.clone()
}

infix fun SExpIR.item(index: Int) = AFieldNumberExpIR().apply {
    this.tuple = this@item.clone()
    this.field = index.toLong()
    this.type = (this@item.type as ATupleTypeIR).types[index - 1].clone()
}

fun SExpIR.applyWith(vararg arguments: SExpIR) = AApplyExpIR().apply {
    val rootType = this@applyWith.type

    this.root = this@applyWith.clone()
    this.args.addAll(arguments.map { it.clone() })
    this.type = when (rootType)
    {
        is AMethodTypeIR -> rootType.result.clone()
        is SSeqTypeIR    -> rootType.seqOf.clone()
        is SMapTypeIR    -> rootType.to.clone()
        else             -> throw IllegalStateException()
    }
}

//endregion

//region SCOPES

fun let(declarations: List<AVarDeclIR>, expression: SExpIR) = ALetDefExpIR().apply {
    this.localDefs.addAll(declarations.map { it.clone() })
    this.exp = expression.clone()
    this.type = expression.type.clone()
}

fun letBeSuchThat(binding: SMultipleBindIR, predicate: SExpIR? = null, expression: SExpIR) = ALetBeStExpIR().apply {
    this.header = AHeaderLetBeStIR().apply {
        this.binding = binding.clone()
        this.suchThat = predicate?.clone()
    }
    this.value = expression.clone()
    this.type = expression.type.clone()
}

//endregion

//region QUANTIFIERS

fun universalQuantifier(bindings: List<SMultipleBindIR>, predicate: SExpIR) = AForAllQuantifierExpIR().apply {
    this.bindList.addAll(bindings.map { it.clone() })
    this.predicate = predicate.clone()
    this.type = boolType
}

fun existentialQuantifier(bindings: List<SMultipleBindIR>, predicate: SExpIR) = AExistsQuantifierExpIR().apply {
    this.bindList.addAll(bindings.map { it.clone() })
    this.predicate = predicate.clone()
    this.type = boolType
}

fun uniqueExistentialQuantifier(bindings: List<SMultipleBindIR>, predicate: SExpIR) = AExists1QuantifierExpIR().apply {
    this.bindList.addAll(bindings.map { it.clone() })
    this.predicate = predicate.clone()
    this.type = boolType
}

//endregion

//region IS

fun isBool(expression: SExpIR) = ABoolIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isChar(expression: SExpIR) = ACharIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isInt(expression: SExpIR) = AIntIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isNat(expression: SExpIR) = ANatIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isNat1(expression: SExpIR) = ANat1IsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isRat(expression: SExpIR) = ARatIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isReal(expression: SExpIR) = ARealIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isToken(expression: SExpIR) = ATokenIsExpIR().apply { this.exp = expression.clone(); this.type = boolType }

fun isTuple(expression: SExpIR, tupleType: STypeIR) = ATupleIsExpIR().apply {
    this.exp = expression.clone()
    this.checkedType = tupleType.clone()
    this.type = boolType
}

fun isExpression(expression: SExpIR, checkedType: STypeIR) = AGeneralIsExpIR().apply {
    this.exp = expression.clone()
    this.checkedType = checkedType.clone()
    this.type = boolType
}

//endregion

//region EXCEPTIONS

val isNotYetSpecifiedExpression: ANotImplementedExpIR
    get() = ANotImplementedExpIR()

val undefinedExpression: AUndefinedExpIR
    get() = AUndefinedExpIR()

//endregion

fun isolate(expression: SExpIR) = AIsolationUnaryExpIR().apply {
    this.type = expression.type.clone()
    this.exp = expression.clone()
}
