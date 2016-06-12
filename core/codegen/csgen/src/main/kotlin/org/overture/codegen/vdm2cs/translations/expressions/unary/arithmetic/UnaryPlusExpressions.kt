package org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic

import org.overture.codegen.ir.expressions.APlusUnaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.unary.CsUnaryPlusExpression
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object UnaryPlusExpressions : UnaryExpressionTranslationRule<APlusUnaryExpIR>
{
    override fun translate(irNode: APlusUnaryExpIR)
        = CsUnaryPlusExpression(irNode.exp.translate)
}
