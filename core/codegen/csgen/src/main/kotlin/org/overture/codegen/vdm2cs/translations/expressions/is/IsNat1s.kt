package org.overture.codegen.vdm2cs.translations.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsIsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object IsNat1s : ExpressionTranslationRule<ANat1IsExpIR>
{
    override fun translate(irNode: ANat1IsExpIR)
        = parseExpression("((${irNode.exp.inline}) as int?) > 0")
}
