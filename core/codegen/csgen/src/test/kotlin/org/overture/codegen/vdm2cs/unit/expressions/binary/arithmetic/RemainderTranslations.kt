package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class RemainderTranslations : ExpressionTranslationRuleSpek<ARemNumericBinaryExpIR>(Remainders)
{
    init
    {
        "1 rem 2" describesThat
            (1.toLiteral rem 2.toLiteral) becomes
            "1 % 2"

        "355 rem 113" describesThat
            (355.toLiteral rem 113.toLiteral) becomes
            "355 % 113"

        "-14 rem 3" describesThat
            (unaryMinus(14.toLiteral) rem 3.toLiteral) becomes
            "-14 % 3"

        "numerator rem denominator" describesThat
            (identifier("numerator", intType) rem identifier("denominator", intType)) becomes
            "numerator % denominator"
    }
}
