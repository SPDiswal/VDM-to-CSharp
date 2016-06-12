package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Reverses : UnaryExpressionTranslationRule<AReverseUnaryExpIR>
{
    override fun translate(irNode: AReverseUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).Reverse().ToList()")
}
