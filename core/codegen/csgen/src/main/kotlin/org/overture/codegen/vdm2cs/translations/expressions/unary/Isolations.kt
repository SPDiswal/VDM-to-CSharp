package org.overture.codegen.vdm2cs.translations.expressions.unary

import org.overture.codegen.ir.expressions.AIsolationUnaryExpIR
import org.overture.codegen.vdm2cs.utilities.translate

object Isolations : UnaryExpressionTranslationRule<AIsolationUnaryExpIR>
{
    override fun translate(irNode: AIsolationUnaryExpIR)
        = irNode.exp.translate
}
