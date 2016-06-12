package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.AGreaterEqualNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.GreaterThanOrEqualToInequalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class GreaterThanOrEqualToTranslations :
    ExpressionTranslationRuleSpek<AGreaterEqualNumericBinaryExpIR>(GreaterThanOrEqualToInequalities)
{
    init
    {
        "1 >= 2" describesThat
            (1.toLiteral isGreaterThanOrEqualTo 2.toLiteral) becomes
            "1 >= 2"

        "355 >= 113" describesThat
            (355.toLiteral isGreaterThanOrEqualTo 113.toLiteral) becomes
            "355 >= 113"

        "operand >= operand" describesThat
            (identifier("operand", intType) isGreaterThanOrEqualTo identifier("operand", intType)) becomes
            "operand >= operand"
    }
}
