package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ATimesNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.Multiplications
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MultiplicationTranslations : ExpressionTranslationRuleSpek<ATimesNumericBinaryExpIR>(Multiplications)
{
    init
    {
        "1 * 2" describesThat
            (1.toLiteral times 2.toLiteral) becomes
            "1 * 2"

        "355 * 113" describesThat
            (355.toLiteral times 113.toLiteral) becomes
            "355 * 113"

        "factor * factor" describesThat
            (identifier("factor", intType) times identifier("factor", intType)) becomes
            "factor * factor"

        "(1.2 * 3.4) * 5.6" describesThat
            ((1.2.toLiteral times 3.4.toLiteral) times 5.6.toLiteral) becomes
            "(1.2m * 3.4m) * 5.6m"

        "1.2 * (3.4 * 5.6)" describesThat
            (1.2.toLiteral times (3.4.toLiteral times 5.6.toLiteral)) becomes
            "1.2m * (3.4m * 5.6m)"
    }
}
