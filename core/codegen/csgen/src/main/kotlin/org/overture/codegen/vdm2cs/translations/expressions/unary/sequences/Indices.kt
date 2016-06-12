package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Indices : UnaryExpressionTranslationRule<AIndicesUnaryExpIR>
{
    override fun translate(irNode: AIndicesUnaryExpIR)
        = parseExpression("Enumerable.Range(1, (${irNode.exp.inline}).Count).ToHashSet()")
}
