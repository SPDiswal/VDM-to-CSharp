package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.`is`.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsRealTranslations : ExpressionTranslationRuleSpek<ARealIsExpIR>(IsReals)
{
    init
    {
        "is_real(1.5)" describesThat
            isReal(1.5.toLiteral) becomes
            "1.5m is decimal"

        "is_real(a)" describesThat
            isReal(identifier("a", realType)) becomes
            "a is decimal"
    }
}
