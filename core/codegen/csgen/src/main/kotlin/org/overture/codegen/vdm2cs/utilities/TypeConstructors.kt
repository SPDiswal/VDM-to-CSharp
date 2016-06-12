package org.overture.codegen.vdm2cs.utilities

import org.overture.ast.types.AOperationType
import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.types.*

val boolType: ABoolBasicTypeIR
    get() = ABoolBasicTypeIR().apply { this.optional = false }

val charType: ACharBasicTypeIR
    get() = ACharBasicTypeIR().apply { this.optional = false }

val intType: AIntNumericBasicTypeIR
    get() = AIntNumericBasicTypeIR().apply { this.optional = false }

val natType: ANatNumericBasicTypeIR
    get() = ANatNumericBasicTypeIR().apply { this.optional = false }

val nat1Type: ANat1NumericBasicTypeIR
    get() = ANat1NumericBasicTypeIR().apply { this.optional = false }

val ratType: ARatNumericBasicTypeIR
    get() = ARatNumericBasicTypeIR().apply { this.optional = false }

val realType: ARealNumericBasicTypeIR
    get() = ARealNumericBasicTypeIR().apply { this.optional = false }

val tokenType: ATokenBasicTypeIR
    get() = ATokenBasicTypeIR().apply { this.optional = false }

fun <TAliasedType : STypeIR> namedType(qualifier: String? = null, name: String, type: TAliasedType)
    = type.clone().apply {
    this.namedInvType = namedTypeDeclaration(qualifier, name, type).decl as ANamedTypeDeclIR
    this.optional = false
}

fun <TInnerType : STypeIR> optional(type: TInnerType)
    = type.clone().apply { this.optional = true }

fun setOf(elementType: STypeIR) = ASetSetTypeIR().apply {
    this.setOf = elementType.clone()
    this.optional = false
}

fun seqOf(elementType: STypeIR) = ASeqSeqTypeIR().apply {
    this.seqOf = elementType.clone()
    this.optional = false
    this.seq1 = false
}

fun seq1Of(elementType: STypeIR) = ASeqSeqTypeIR().apply {
    this.seqOf = elementType.clone()
    this.optional = false
    this.seq1 = true
}

fun map(fromToPair: Pair<STypeIR, STypeIR>) = AMapMapTypeIR().apply {
    this.from = fromToPair.first.clone()
    this.to = fromToPair.second.clone()
    this.optional = false
    this.injective = false
}

fun inmap(fromToPair: Pair<STypeIR, STypeIR>) = AMapMapTypeIR().apply {
    this.from = fromToPair.first.clone()
    this.to = fromToPair.second.clone()
    this.optional = false
    this.injective = true
}

fun tupleOf(vararg types: STypeIR)
    = ATupleTypeIR().apply { this.types.addAll(types.map { it.clone() }); this.optional = false }

fun unionOf(vararg types: STypeIR)
    = AUnionTypeIR().apply { this.types.addAll(types.map { it.clone() }); this.optional = false }

fun recordType(qualifier: String? = null, name: String)
    = ARecordTypeIR().apply { this.name = typeName(qualifier, name); this.optional = false }

fun quoteType(name: String)
    = AQuoteTypeIR().apply { this.value = name; this.optional = false }

fun methodType(parameterTypes: List<STypeIR>, resultType: STypeIR) = AMethodTypeIR().apply {
    this.params.addAll(parameterTypes.map { it.clone() })
    this.result = resultType.clone()
    this.optional = false
}

fun pureMethodType(parameterTypes: List<STypeIR>, resultType: STypeIR) = AMethodTypeIR().apply {
    this.params.addAll(parameterTypes.map { it.clone() })
    this.result = resultType.clone()
    this.optional = false
    this.equivalent = AOperationType().apply { this.pure = true }
}

fun templateType(name: String) = ATemplateTypeIR().apply { this.name = name }

val voidType: AVoidTypeIR
    get() = AVoidTypeIR().apply { this.optional = false }

val stringType: AStringTypeIR
    get() = AStringTypeIR().apply { this.optional = false }

val unknownType: AUnknownTypeIR
    get() = AUnknownTypeIR().apply { this.optional = false }

fun classType(className: String) = AClassTypeIR().apply { this.name = className; this.optional = false }
