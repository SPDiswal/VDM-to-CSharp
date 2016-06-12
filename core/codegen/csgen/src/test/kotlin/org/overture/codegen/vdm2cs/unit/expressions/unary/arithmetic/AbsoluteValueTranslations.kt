package org.overture.codegen.vdm2cs.unit.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class AbsoluteValueTranslations : ExpressionTranslationRuleSpek<AAbsUnaryExpIR>(AbsoluteValues)
{
    init
    {
        "abs -1" describesThat
            abs(unaryMinus(1.toLiteral)) becomes
            "Math.Abs(-1)"

        "abs 42" describesThat
            abs(42.toLiteral) becomes
            "Math.Abs(42)"

        "abs -3.14" describesThat
            abs(unaryMinus(3.14.toLiteral)) becomes
            "Math.Abs(-3.14m)"

        "abs number" describesThat
            abs(identifier("number", intType)) becomes
            "Math.Abs(number)"

        "abs (1 + 2)" describesThat
            abs(1.toLiteral plus 2.toLiteral) becomes
            "Math.Abs(1 + 2)"
    }
}
