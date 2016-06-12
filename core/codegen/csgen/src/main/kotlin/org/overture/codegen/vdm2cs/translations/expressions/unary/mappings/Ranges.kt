package org.overture.codegen.vdm2cs.translations.expressions.unary.mappings

import org.overture.codegen.ir.expressions.AMapRangeUnaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Ranges : UnaryExpressionTranslationRule<AMapRangeUnaryExpIR>
{
    override fun translate(irNode: AMapRangeUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).Values.ToHashSet()")
}
