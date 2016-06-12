package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.AGreaterNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.GreaterThanInequalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class GreaterThanTranslations : ExpressionTranslationRuleSpek<AGreaterNumericBinaryExpIR>(GreaterThanInequalities)
{
    init
    {
        "1 > 2" describesThat
            (1.toLiteral isGreaterThan 2.toLiteral) becomes
            "1 > 2"

        "355 > 113" describesThat
            (355.toLiteral isGreaterThan 113.toLiteral) becomes
            "355 > 113"

        "operand > operand" describesThat
            (identifier("operand", intType) isGreaterThan identifier("operand", intType)) becomes
            "operand > operand"
    }
}
