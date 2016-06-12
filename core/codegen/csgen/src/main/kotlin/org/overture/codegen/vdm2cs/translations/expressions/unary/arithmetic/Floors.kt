package org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.AFloorUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Floors : UnaryExpressionTranslationRule<AFloorUnaryExpIR>
{
    override fun translate(irNode: AFloorUnaryExpIR)
        = parseExpression("(int) Math.Floor(${irNode.exp.inline})")
}
