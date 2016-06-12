package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.ACharLiteralExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.literals.CsCharacterExpression

object CharacterLiterals : LiteralTranslationRule<ACharLiteralExpIR>
{
    override fun translate(irNode: ACharLiteralExpIR)
        = CsCharacterExpression(irNode.value.toString())
}
