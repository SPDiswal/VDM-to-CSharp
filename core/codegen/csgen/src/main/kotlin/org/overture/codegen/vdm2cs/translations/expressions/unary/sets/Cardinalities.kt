package org.overture.codegen.vdm2cs.translations.expressions.unary.sets

import org.overture.codegen.ir.expressions.ACardUnaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Cardinalities : UnaryExpressionTranslationRule<ACardUnaryExpIR>
{
    override fun translate(irNode: ACardUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).Count")
}
