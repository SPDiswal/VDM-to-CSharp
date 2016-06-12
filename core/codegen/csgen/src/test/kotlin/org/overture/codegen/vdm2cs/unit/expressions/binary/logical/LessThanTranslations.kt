package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.ALessNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.LessThanInequalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class LessThanTranslations : ExpressionTranslationRuleSpek<ALessNumericBinaryExpIR>(LessThanInequalities)
{
    init
    {
        "1 < 2" describesThat
            (1.toLiteral isLessThan 2.toLiteral) becomes
            "1 < 2"

        "355 < 113" describesThat
            (355.toLiteral isLessThan 113.toLiteral) becomes
            "355 < 113"

        "operand < operand" describesThat
            (identifier("operand", intType) isLessThan identifier("operand", intType)) becomes
            "operand < operand"
    }
}
