package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.literals.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class QuoteLiteralTranslations : ExpressionTranslationRuleSpek<AQuoteLiteralExpIR>(QuoteLiterals)
{
    init
    {
        "<$nextPlaceholder>" describesThat
            quote(placeholder) becomes
            "Quote.$placeholder"

        "<$nextLowerCasePlaceholder>" describesThat
            quote(lowerCasePlaceholder) becomes
            "Quote.$placeholder"
    }
}
