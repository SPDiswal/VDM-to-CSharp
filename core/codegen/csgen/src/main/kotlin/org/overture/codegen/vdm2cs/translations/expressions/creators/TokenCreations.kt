package org.overture.codegen.vdm2cs.translations.expressions.creators

import org.overture.codegen.ir.expressions.AMkBasicExpIR
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object TokenCreations : ExpressionTranslationRule<AMkBasicExpIR>
{
    override fun translate(irNode: AMkBasicExpIR)
        = parseExpression("Token.Create(${irNode.arg.inline})")
}
