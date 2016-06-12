package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.ANullExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.CsNullExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object NullLiterals : ExpressionTranslationRule<ANullExpIR>
{
    override fun translate(irNode: ANullExpIR)
        = CsNullExpression
}
