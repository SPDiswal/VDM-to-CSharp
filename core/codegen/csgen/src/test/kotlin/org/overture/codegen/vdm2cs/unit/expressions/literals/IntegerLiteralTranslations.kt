package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.AIntLiteralExpIR
import org.overture.codegen.vdm2cs.translations.expressions.literals.IntLiterals
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.toLiteral

class IntegerLiteralTranslations : ExpressionTranslationRuleSpek<AIntLiteralExpIR>(IntLiterals)
{
    init
    {
        "42" describesThat
            42.toLiteral becomes
            "42"

        "1337" describesThat
            1337.toLiteral becomes
            "1337"
    }
}
