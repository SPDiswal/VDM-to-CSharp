package org.overture.codegen.vdm2cs.unit.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.APlusUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic.UnaryPlusExpressions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class UnaryPlusTranslations : ExpressionTranslationRuleSpek<APlusUnaryExpIR>(UnaryPlusExpressions)
{
    init
    {
        "+1" describesThat
            unaryPlus(1.toLiteral) becomes
            "+1"

        "+42" describesThat
            unaryPlus(42.toLiteral) becomes
            "+42"

        "+number" describesThat
            unaryPlus(identifier("number", intType)) becomes
            "+number"

        "++1" describesThat
            unaryPlus(unaryPlus(1.toLiteral)) becomes
            "+(+1)"

        "+(1 + 2)" describesThat
            unaryPlus(1.toLiteral plus 2.toLiteral) becomes
            "+(1 + 2)"
    }
}
