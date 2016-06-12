package org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.AMinusUnaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.unary.CsUnaryMinusExpression
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object UnaryMinusExpressions : UnaryExpressionTranslationRule<AMinusUnaryExpIR>
{
    override fun translate(irNode: AMinusUnaryExpIR)
        = CsUnaryMinusExpression(irNode.exp.translate)
}
