package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.`is`.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsNat1Translations : ExpressionTranslationRuleSpek<ANat1IsExpIR>(IsNat1s)
{
    init
    {
        "is_nat1(1)" describesThat
            isNat1(1.toLiteral) becomes
            "(1 as int?) > 0"

        "is_nat1(a)" describesThat
            isNat1(identifier("a", nat1Type)) becomes
            "(a as int?) > 0"

        "is_nat1(1 + 1)" describesThat
            isNat1(1.toLiteral plus 1.toLiteral) becomes
            "(1 + 1 as int?) > 0"
    }
}
