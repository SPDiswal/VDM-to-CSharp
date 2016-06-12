package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.SExpIR
import org.overture.codegen.ir.expressions.*

//region LITERALS

val SLiteralExpIR.lowerBoundValue: Int
    get() = when (this)
    {
        is AIntLiteralExpIR  -> this.value.toInt()
        is ARealLiteralExpIR -> Math.ceil(this.value).toInt()
        else                 -> throw IllegalStateException()
    }

val SLiteralExpIR.upperBoundValue: Int
    get() = when (this)
    {
        is AIntLiteralExpIR  -> this.value.toInt()
        is ARealLiteralExpIR -> Math.floor(this.value).toInt()
        else                 -> throw IllegalStateException()
    }

//endregion

//region SETS

val SExpIR.isEmptySet: Boolean
    get() = this is AEnumSetExpIR && this.members.isEmpty()

val SExpIR.isSingletonSet: Boolean
    get() = this is AEnumSetExpIR && this.members.size == 1

//endregion

//region SEQUENCES

val SExpIR.isEmptySequence: Boolean
    get() = this is AEnumSeqExpIR && this.members.isEmpty()

val SExpIR.isSingletonSequence: Boolean
    get() = this is AEnumSeqExpIR && this.members.size == 1

//endregion

//region MAPPINGS

val SExpIR.isEmptyMapping: Boolean
    get() = this is AEnumMapExpIR && this.members.isEmpty()

val SExpIR.isSingletonMapping: Boolean
    get() = this is AEnumMapExpIR && this.members.size == 1

//endregion

val SExpIR.getSingleElement: SExpIR
    get() = when (this)
    {
        is AEnumSetExpIR -> members.single()
        is AEnumSeqExpIR -> members.single()
        is AEnumMapExpIR -> members.single()
        else             -> throw IllegalStateException()
    }
