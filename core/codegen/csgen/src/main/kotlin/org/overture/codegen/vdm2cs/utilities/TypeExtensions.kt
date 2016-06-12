package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.types.*

val STypeIR.isNullable: Boolean
    get() = this is AClassTypeIR ||
            this is ARecordTypeIR ||
            this is SMapTypeIR ||
            this is SSeqTypeIR ||
            this is SSetTypeIR ||
            this is AStringTypeIR ||
            this is ATokenBasicTypeIR ||
            this is ATupleTypeIR ||
            this is AUnionTypeIR

val STypeIR.isStringType: Boolean
    get() = this is AStringTypeIR ||
            this is SSeqTypeIR && this.seqOf is ACharBasicTypeIR

val STypeIR.isNamedType: Boolean
    get() = namedInvType != null

val STypeIR.isOptional: Boolean
    get() = optional ?: false

val ASeqSeqTypeIR.isNonEmpty: Boolean
    get() = seq1 ?: false

val AMapMapTypeIR.isInjective: Boolean
    get() = injective ?: false

val AUnionTypeIR.isNonOptionalUnionOfQuotes: Boolean
    get() = !isOptional && types.all { it is AQuoteTypeIR }

val AUnionTypeIR.isOptionalUnionOfQuotes: Boolean
    get() = isOptional && types.all { it is AQuoteTypeIR }
