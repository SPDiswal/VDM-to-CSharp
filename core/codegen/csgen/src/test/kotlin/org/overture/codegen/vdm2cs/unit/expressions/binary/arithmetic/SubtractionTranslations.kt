package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ASubtractNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.Subtractions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SubtractionTranslations : ExpressionTranslationRuleSpek<ASubtractNumericBinaryExpIR>(Subtractions)
{
    init
    {
        "1 - 2" describesThat
            (1.toLiteral minus 2.toLiteral) becomes
            "1 - 2"

        "355 - 113" describesThat
            (355.toLiteral minus 113.toLiteral) becomes
            "355 - 113"

        "minuend - subtrahend" describesThat
            (identifier("minuend", intType) minus identifier("subtrahend", intType)) becomes
            "minuend - subtrahend"

        "(1.2 - 3.4) - 5.6" describesThat
            ((1.2.toLiteral minus 3.4.toLiteral) minus 5.6.toLiteral) becomes
            "(1.2m - 3.4m) - 5.6m"

        "1.2 - (3.4 - 5.6)" describesThat
            (1.2.toLiteral minus (3.4.toLiteral minus 5.6.toLiteral)) becomes
            "1.2m - (3.4m - 5.6m)"
    }
}
