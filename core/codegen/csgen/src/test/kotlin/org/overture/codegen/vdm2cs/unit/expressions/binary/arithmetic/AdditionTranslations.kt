package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.APlusNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.Additions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class AdditionTranslations : ExpressionTranslationRuleSpek<APlusNumericBinaryExpIR>(Additions)
{
    init
    {
        "1 + 2" describesThat
            (1.toLiteral plus 2.toLiteral) becomes
            "1 + 2"

        "355 + 113" describesThat
            (355.toLiteral plus 113.toLiteral) becomes
            "355 + 113"

        "addend + addend" describesThat
            (identifier("addend", intType) plus identifier("addend", intType)) becomes
            "addend + addend"

        "(1.2 + 3.4) + 5.6" describesThat
            ((1.2.toLiteral plus 3.4.toLiteral) plus 5.6.toLiteral) becomes
            "(1.2m + 3.4m) + 5.6m"

        "1.2 + (3.4 + 5.6)" describesThat
            (1.2.toLiteral plus (3.4.toLiteral plus 5.6.toLiteral)) becomes
            "1.2m + (3.4m + 5.6m)"
    }
}
