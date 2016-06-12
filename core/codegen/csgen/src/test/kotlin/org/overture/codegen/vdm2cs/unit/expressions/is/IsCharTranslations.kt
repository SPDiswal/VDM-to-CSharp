package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.`is`.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsCharTranslations : ExpressionTranslationRuleSpek<ACharIsExpIR>(IsChars)
{
    init
    {
        "is_char('A')" describesThat
            isChar('A'.toLiteral) becomes
            "'A' is char"

        "is_char(a)" describesThat
            isChar(identifier("a", charType)) becomes
            "a is char"
    }
}
