package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.AStringLiteralExpIR
import org.overture.codegen.vdm2cs.translations.expressions.literals.StringLiterals
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.toLiteral

class StringLiteralTranslations : ExpressionTranslationRuleSpek<AStringLiteralExpIR>(StringLiterals)
{
    init
    {
        "\"Hello, World!\"" describesThat
            "Hello, World!".toLiteral becomes
            "\"Hello, World!\""

        "\"Double quote (\\\") escaped\"" describesThat
            "Double quote (\\\") escaped".toLiteral becomes
            "\"Double quote (\\\") escaped\""

        // TODO Escape sequences
    }
}
