package org.overture.codegen.vdm2cs.unit.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class FloorTranslations : ExpressionTranslationRuleSpek<AFloorUnaryExpIR>(Floors)
{
    init
    {
        "floor -1" describesThat
            floor(unaryMinus(1.toLiteral)) becomes
            "(int) Math.Floor(-1)"

        "floor 42" describesThat
            floor(42.toLiteral) becomes
            "(int) Math.Floor(42)"

        "floor -3.14" describesThat
            floor(unaryMinus(3.14.toLiteral)) becomes
            "(int) Math.Floor(-3.14m)"

        "floor number" describesThat
            floor(identifier("number", intType)) becomes
            "(int) Math.Floor(number)"

        "floor (1 + 2)" describesThat
            floor(1.toLiteral plus 2.toLiteral) becomes
            "(int) Math.Floor(1 + 2)"
    }
}
