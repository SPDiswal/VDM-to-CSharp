package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.ARealLiteralExpIR
import org.overture.codegen.vdm2cs.translations.expressions.literals.RatLiterals
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.toLiteral

class RatLiteralTranslations : ExpressionTranslationRuleSpek<ARealLiteralExpIR>(RatLiterals)
{
    init
    {
        "0,11" describesThat
            0.11.toLiteral becomes
            "0.11m"

        "0,123450" describesThat
            0.123450.toLiteral becomes
            "0.12345m"

        "0,79" describesThat
            0.79.toLiteral becomes
            "0.79m"

        "42,0" describesThat
            42.0.toLiteral becomes
            "42.0m"

        "1337,5" describesThat
            1337.5.toLiteral becomes
            "1337.5m"
    }
}
