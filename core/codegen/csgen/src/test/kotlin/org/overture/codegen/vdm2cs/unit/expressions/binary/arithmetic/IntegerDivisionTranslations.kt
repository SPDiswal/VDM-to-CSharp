package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IntegerDivisionTranslations : ExpressionTranslationRuleSpek<AIntDivNumericBinaryExpIR>(IntegerDivisions)
{
    init
    {
        "1 div 2" describesThat
            (1.toLiteral div 2.toLiteral) becomes
            "1 / 2"

        "355 div 113" describesThat
            (355.toLiteral div 113.toLiteral) becomes
            "355 / 113"

        "-14 div 3" describesThat
            (unaryMinus(14.toLiteral) div 3.toLiteral) becomes
            "-14 / 3"

        "numerator div denominator" describesThat
            (identifier("numerator", intType) div identifier("denominator", intType)) becomes
            "numerator / denominator"
    }
}
