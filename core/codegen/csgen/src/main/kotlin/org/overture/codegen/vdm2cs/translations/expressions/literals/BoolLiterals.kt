package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.ABoolLiteralExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.CsBooleanExpression

object BoolLiterals : LiteralTranslationRule<ABoolLiteralExpIR>
{
    override fun translate(irNode: ABoolLiteralExpIR)
        = CsBooleanExpression(irNode.value)
}
