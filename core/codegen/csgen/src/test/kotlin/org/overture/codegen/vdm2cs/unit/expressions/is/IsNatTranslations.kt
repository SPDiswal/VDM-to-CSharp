package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.`is`.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsNatTranslations : ExpressionTranslationRuleSpek<ANatIsExpIR>(IsNats)
{
    init
    {
        "is_nat(1)" describesThat
            isNat(1.toLiteral) becomes
            "(1 as int?) >= 0"

        "is_nat(a)" describesThat
            isNat(identifier("a", natType)) becomes
            "(a as int?) >= 0"

        "is_nat(1 + 1)" describesThat
            isNat(1.toLiteral plus 1.toLiteral) becomes
            "(1 + 1 as int?) >= 0"
    }
}
