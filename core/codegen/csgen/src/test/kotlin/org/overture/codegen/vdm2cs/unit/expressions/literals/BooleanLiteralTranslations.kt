package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.ABoolLiteralExpIR
import org.overture.codegen.vdm2cs.translations.expressions.literals.BoolLiterals
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.toLiteral

class BooleanLiteralTranslations : ExpressionTranslationRuleSpek<ABoolLiteralExpIR>(BoolLiterals)
{
    init
    {
        "true" describesThat
            true.toLiteral becomes
            "true"

        "false" describesThat
            false.toLiteral becomes
            "false"
    }
}
