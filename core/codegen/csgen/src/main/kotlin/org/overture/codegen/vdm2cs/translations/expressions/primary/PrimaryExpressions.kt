package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.SExpIR
import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object PrimaryExpressions : ExpressionTranslationRule<SExpIR>
{
    override fun translate(irNode: SExpIR) = when (irNode)
    {
    // IDENTIFIERS
        is SVarExpIR         -> Identifiers.translate(irNode)

    // MEMBER LOOKUPS
        is AFieldExpIR       -> MemberLookups.translate(irNode)
        is AFieldNumberExpIR -> ItemLookups.translate(irNode)

    // APPLICATIONS
        is AApplyExpIR       -> Applications.translate(irNode)

        else                 -> throw UnsupportedTranslationException(irNode)
    }
}
