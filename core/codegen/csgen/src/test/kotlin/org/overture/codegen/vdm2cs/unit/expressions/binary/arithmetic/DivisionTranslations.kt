package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ADivideNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.Divisions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DivisionTranslations : ExpressionTranslationRuleSpek<ADivideNumericBinaryExpIR>(Divisions)
{
    init
    {
        "1 / 2" describesThat
            (1.toLiteral over 2.toLiteral) becomes
            "1 / 2"

        "355 / 113" describesThat
            (355.toLiteral over 113.toLiteral) becomes
            "355 / 113"

        "numerator / denominator" describesThat
            (identifier("numerator", intType) over identifier("denominator", intType)) becomes
            "numerator / denominator"

        "(1.2 / 3.4) / 5.6" describesThat
            ((1.2.toLiteral over 3.4.toLiteral) over 5.6.toLiteral) becomes
            "(1.2m / 3.4m) / 5.6m"

        "1.2 / (3.4 / 5.6)" describesThat
            (1.2.toLiteral over (3.4.toLiteral over 5.6.toLiteral)) becomes
            "1.2m / (3.4m / 5.6m)"
    }
}
