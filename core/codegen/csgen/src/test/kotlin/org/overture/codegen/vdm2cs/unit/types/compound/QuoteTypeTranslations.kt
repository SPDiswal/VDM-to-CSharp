package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.AQuoteTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.QuoteTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.quoteType

class QuoteTypeTranslations : TypeTranslationRuleSpek<AQuoteTypeIR>(QuoteTypes)
{
    init
    {
        "<$nextPlaceholder>" describesThat
            quoteType(name = placeholder) becomes
            "Quote"

        "<$nextLowerCasePlaceholder>" describesThat
            quoteType(name = lowerCasePlaceholder) becomes
            "Quote"
    }
}
