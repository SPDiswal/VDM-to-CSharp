package org.overture.codegen.vdm2cs.unit.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.AMinusUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic.UnaryMinusExpressions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class UnaryMinusTranslations : ExpressionTranslationRuleSpek<AMinusUnaryExpIR>(UnaryMinusExpressions)
{
    init
    {
        "-1" describesThat
            unaryMinus(1.toLiteral) becomes
            "-1"

        "-42" describesThat
            unaryMinus(42.toLiteral) becomes
            "-42"

        "-number" describesThat
            unaryMinus(identifier("number", intType)) becomes
            "-number"

        "--1" describesThat
            unaryMinus(unaryMinus(1.toLiteral)) becomes
            "-(-1)"

        "-(1 + 2)" describesThat
            unaryMinus(1.toLiteral plus 2.toLiteral) becomes
            "-(1 + 2)"
    }
}
