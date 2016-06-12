package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Heads : UnaryExpressionTranslationRule<AHeadUnaryExpIR>
{
    override fun translate(irNode: AHeadUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).First()")
}
