package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Tails : UnaryExpressionTranslationRule<ATailUnaryExpIR>
{
    override fun translate(irNode: ATailUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).Skip(1).ToList()")
}
