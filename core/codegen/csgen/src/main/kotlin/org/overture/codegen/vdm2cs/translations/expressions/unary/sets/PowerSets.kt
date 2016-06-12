package org.overture.codegen.vdm2cs.translations.expressions.unary.sets

import org.overture.codegen.ir.expressions.APowerSetUnaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object PowerSets : UnaryExpressionTranslationRule<APowerSetUnaryExpIR>
{
    override fun translate(irNode: APowerSetUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).PowerSet()")
}
