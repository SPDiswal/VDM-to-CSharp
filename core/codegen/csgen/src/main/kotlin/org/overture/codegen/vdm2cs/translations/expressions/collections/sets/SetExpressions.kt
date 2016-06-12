package org.overture.codegen.vdm2cs.translations.expressions.collections.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object SetExpressions : ExpressionTranslationRule<SSetExpIR>
{
    override fun translate(irNode: SSetExpIR) = when (irNode)
    {
        is ACompSetExpIR  -> SetComprehensions.translate(irNode)
        is AEnumSetExpIR  -> SetEnumerations.translate(irNode)
        is ARangeSetExpIR -> SetRanges.translate(irNode)
        else              -> throw UnsupportedTranslationException(irNode)
    }
}
