package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.ARealLiteralExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.CsDecimalExpression

object RatLiterals : LiteralTranslationRule<ARealLiteralExpIR>
{
    override fun translate(irNode: ARealLiteralExpIR) = let {
        val (integralDigits, fractionalDigits) = String.format("%s", irNode.value).split(".")
        CsDecimalExpression(integralDigits, fractionalDigits)
    }
}
