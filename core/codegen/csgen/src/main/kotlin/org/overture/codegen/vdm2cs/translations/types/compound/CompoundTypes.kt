package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.translations.types.other.*

object CompoundTypes : TypeTranslationRule<STypeIR>
{
    override fun translate(irNode: STypeIR) = when (irNode)
    {
        is ASetSetTypeIR   -> SetTypes.translate(irNode)
        is SSeqTypeIR      -> SeqTypes.translate(irNode)
        is SMapTypeIR      -> MapTypes.translate(irNode)
        is ATupleTypeIR    -> ProductTypes.translate(irNode)
        is AUnionTypeIR    -> UnionTypes.translate(irNode)
        is AQuoteTypeIR    -> QuoteTypes.translate(irNode)
        is ARecordTypeIR   -> CompositeTypes.translate(irNode)
        is AMethodTypeIR   -> FunctionTypes.translate(irNode)
        else               -> throw UnsupportedTranslationException(irNode)
    }
}
