package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.ARatIsExpIR
import org.overture.codegen.vdm2cs.translations.expressions.`is`.IsRats
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsRatTranslations : ExpressionTranslationRuleSpek<ARatIsExpIR>(IsRats)
{
    init
    {
        "is_rat(1.5)" describesThat
            isRat(1.5.toLiteral) becomes
            "1.5m is decimal"

        "is_rat(a)" describesThat
            isRat(identifier("a", ratType)) becomes
            "a is decimal"
    }
}
