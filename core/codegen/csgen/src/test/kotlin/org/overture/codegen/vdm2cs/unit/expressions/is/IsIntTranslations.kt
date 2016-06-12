package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.AIntIsExpIR
import org.overture.codegen.vdm2cs.translations.expressions.`is`.IsInts
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsIntTranslations : ExpressionTranslationRuleSpek<AIntIsExpIR>(IsInts)
{
    init
    {
        "is_int(1)" describesThat
            isInt(1.toLiteral) becomes
            "1 is int"

        "is_int(a)" describesThat
            isInt(identifier("a", intType)) becomes
            "a is int"
    }
}
