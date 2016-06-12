package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.AIntLiteralExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.CsIntegerExpression

object IntLiterals : LiteralTranslationRule<AIntLiteralExpIR>
{
    override fun translate(irNode: AIntLiteralExpIR)
        = CsIntegerExpression(irNode.value.toString())
}
