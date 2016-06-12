package org.overture.codegen.vdm2cs.translations.expressions.unary.logical

import org.overture.codegen.ir.expressions.ANotUnaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.unary.CsNotExpression
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Negations : UnaryExpressionTranslationRule<ANotUnaryExpIR>
{
    override fun translate(irNode: ANotUnaryExpIR)
        = CsNotExpression(irNode.exp.translate)
}
