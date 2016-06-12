package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Elements : UnaryExpressionTranslationRule<AElemsUnaryExpIR>
{
    override fun translate(irNode: AElemsUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).ToHashSet()")
}
