package org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.AAbsUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object AbsoluteValues : UnaryExpressionTranslationRule<AAbsUnaryExpIR>
{
    override fun translate(irNode: AAbsUnaryExpIR)
        = parseExpression("Math.Abs(${irNode.exp.inline})")
}
