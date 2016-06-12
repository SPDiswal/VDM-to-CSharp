package org.overture.codegen.vdm2cs.unit.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ModulusTranslations : ExpressionTranslationRuleSpek<AModNumericBinaryExpIR>(Modulus)
{
    init
    {
        "1 mod 2" describesThat
            (1.toLiteral mod 2.toLiteral) becomes
            "1.Modulo(2)"

        "355 mod 113" describesThat
            (355.toLiteral mod 113.toLiteral) becomes
            "355.Modulo(113)"

        "-14 mod 3" describesThat
            ((-14).toLiteral mod 3.toLiteral) becomes
            "(-14).Modulo(3)"

        "numerator mod denominator" describesThat
            (identifier("numerator", intType) mod identifier("denominator", intType)) becomes
            "numerator.Modulo(denominator)"
    }
}
