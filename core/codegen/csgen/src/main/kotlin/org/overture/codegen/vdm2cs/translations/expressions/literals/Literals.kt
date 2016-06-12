package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException

object Literals : LiteralTranslationRule<SLiteralExpIR>
{
    override fun translate(irNode: SLiteralExpIR) = when (irNode)
    {
        is ABoolLiteralExpIR   -> BoolLiterals.translate(irNode)
        is AIntLiteralExpIR    -> IntLiterals.translate(irNode)
        is ARealLiteralExpIR   -> RatLiterals.translate(irNode)
        is ACharLiteralExpIR   -> CharacterLiterals.translate(irNode)
        is AStringLiteralExpIR -> StringLiterals.translate(irNode)
        is AQuoteLiteralExpIR  -> QuoteLiterals.translate(irNode)
        else                   -> throw UnsupportedTranslationException(irNode)
    }
}
