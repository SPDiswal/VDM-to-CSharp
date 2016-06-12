package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.ALessEqualNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.LessThanOrEqualToInequalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class LessThanOrEqualToTranslations :
    ExpressionTranslationRuleSpek<ALessEqualNumericBinaryExpIR>(LessThanOrEqualToInequalities)
{
    init
    {
        "1 <= 2" describesThat
            (1.toLiteral isLessThanOrEqualTo 2.toLiteral) becomes
            "1 <= 2"

        "355 <= 113" describesThat
            (355.toLiteral isLessThanOrEqualTo 113.toLiteral) becomes
            "355 <= 113"

        "operand <= operand" describesThat
            (identifier("operand", intType) isLessThanOrEqualTo identifier("operand", intType)) becomes
            "operand <= operand"
    }
}
