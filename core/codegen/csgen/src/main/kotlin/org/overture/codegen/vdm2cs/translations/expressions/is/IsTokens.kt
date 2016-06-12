package org.overture.codegen.vdm2cs.translations.expressions.`is`

import org.overture.codegen.ir.expressions.ATokenIsExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsIsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object IsTokens : ExpressionTranslationRule<ATokenIsExpIR>
{
    override fun translate(irNode: ATokenIsExpIR)
        = CsIsExpression(irNode.exp.translate, parseType("Token"))
}
