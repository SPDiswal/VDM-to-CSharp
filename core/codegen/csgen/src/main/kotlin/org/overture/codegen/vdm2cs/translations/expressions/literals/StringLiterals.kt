package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.AStringLiteralExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.CsStringExpression

object StringLiterals : LiteralTranslationRule<AStringLiteralExpIR>
{
    override fun translate(irNode: AStringLiteralExpIR)
        = CsStringExpression(irNode.value)
}
