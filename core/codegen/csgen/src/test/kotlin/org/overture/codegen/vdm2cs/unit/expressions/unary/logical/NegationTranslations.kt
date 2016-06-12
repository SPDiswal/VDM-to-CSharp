package org.overture.codegen.vdm2cs.unit.expressions.unary.logical

import org.overture.codegen.ir.expressions.ANotUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class NegationTranslations : ExpressionTranslationRuleSpek<ANotUnaryExpIR>(Negations)
{
    init
    {
        "not true" describesThat
            not(true.toLiteral) becomes
            "!true"

        "not false" describesThat
            not(false.toLiteral) becomes
            "!false"

        "not operand" describesThat
            not(identifier("operand", boolType)) becomes
            "!operand"

        "not not true" describesThat
            not(not(true.toLiteral)) becomes
            "!(!true)"

        "not (true and false)" describesThat
            not(true.toLiteral and false.toLiteral) becomes
            "!(true && false)"
    }
}
