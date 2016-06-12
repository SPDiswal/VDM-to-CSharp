package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.SMultipleBindIR
import org.overture.codegen.ir.patterns.ASetMultipleBindIR
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException

object MultipleBindings : MultipleBindingTranslationRule<SMultipleBindIR>
{
    override fun translate(irNode: SMultipleBindIR) = when (irNode)
    {
        is ASetMultipleBindIR -> MultipleSetBindings.translate(irNode)
//        is ATypeMultipleBindIR -> MultipleTypeBindings.translate(irNode)
        else                  -> throw UnsupportedTranslationException(irNode)
    }
}
