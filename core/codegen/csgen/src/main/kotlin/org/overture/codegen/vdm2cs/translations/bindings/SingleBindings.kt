package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.SBindIR
import org.overture.codegen.ir.patterns.ASetBindIR
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException

object SingleBindings : SingleBindingTranslationRule<SBindIR>
{
    override fun translate(irNode: SBindIR) = when (irNode)
    {
        is ASetBindIR -> SingleSetBindings.translate(irNode)
//        is ATypeBindIR -> SingleTypeBindings.translate(irNode)
        else          -> throw UnsupportedTranslationException(irNode)
    }
}
