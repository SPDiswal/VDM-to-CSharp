package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.ABoolIsExpIR
import org.overture.codegen.vdm2cs.translations.expressions.`is`.IsBools
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsBoolTranslations : ExpressionTranslationRuleSpek<ABoolIsExpIR>(IsBools)
{
    init
    {
        "is_bool(true)" describesThat
            isBool(true.toLiteral) becomes
            "true is bool"

        "is_bool(a)" describesThat
            isBool(identifier("a", boolType)) becomes
            "a is bool"
    }
}
