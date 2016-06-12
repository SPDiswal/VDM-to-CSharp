package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.ALenUnaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Lengths : UnaryExpressionTranslationRule<ALenUnaryExpIR>
{
    override fun translate(irNode: ALenUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).Count")
}
