package org.overture.codegen.vdm2cs.translations.expressions.quantifiers

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException

object Quantifiers : QuantifierTranslationRule<SQuantifierExpIR>
{
    override fun translate(irNode: SQuantifierExpIR) = when (irNode)
    {
        is AForAllQuantifierExpIR  -> UniversalQuantifiers.translate(irNode)
        is AExistsQuantifierExpIR  -> ExistentialQuantifiers.translate(irNode)
        is AExists1QuantifierExpIR -> UniqueExistentialQuantifiers.translate(irNode)
        else                       -> throw UnsupportedTranslationException(irNode)
    }
}
