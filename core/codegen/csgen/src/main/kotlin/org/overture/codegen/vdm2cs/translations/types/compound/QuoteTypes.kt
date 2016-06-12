package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.AQuoteTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object QuoteTypes : TypeTranslationRule<AQuoteTypeIR>
{
    override fun translate(irNode: AQuoteTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("Quote?")
        else               -> parseType("Quote")
    }
}
