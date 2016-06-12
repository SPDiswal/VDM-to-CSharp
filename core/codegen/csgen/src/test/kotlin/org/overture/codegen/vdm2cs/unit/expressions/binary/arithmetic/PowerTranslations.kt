package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class PowerTranslations : ExpressionTranslationRuleSpek<APowerNumericBinaryExpIR>(Powers)
{
    init
    {
        "1 ** 2" describesThat
            (1.toLiteral raisedTo 2.toLiteral) becomes
            "1.IntPower(2)"

        "355 ** 113" describesThat
            (355.toLiteral raisedTo 113.toLiteral) becomes
            "355.IntPower(113)"

        "base ** exponent" describesThat
            (identifier("base", ratType) raisedTo identifier("exponent", intType)) becomes
            "@base.IntPower(exponent)"

        "1.2 ** 6" describesThat
            (1.2.toLiteral raisedTo 6.toLiteral) becomes
            "1.2m.IntPower(6)"

        "1.2 ** 3.4" describesThat
            (1.2.toLiteral raisedTo 3.4.toLiteral) throws
            "There is no translation for powers of fractional exponents available yet."
    }
}
