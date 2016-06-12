package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object Identifiers : ExpressionTranslationRule<SVarExpIR>
{
    override fun translate(irNode: SVarExpIR) = when (irNode)
    {
        is AIdentifierVarExpIR -> UnqualifiedIdentifiers.translate(irNode)
        is AExplicitVarExpIR   -> QualifiedIdentifiers.translate(irNode)
        else                   -> throw UnsupportedTranslationException(irNode)
    }
}
