package org.overture.codegen.vdm2cs.translations.expressions.collections.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object SequenceExpressions : ExpressionTranslationRule<SSeqExpIR>
{
    override fun translate(irNode: SSeqExpIR) = when (irNode)
    {
        is ACompSeqExpIR -> SequenceComprehensions.translate(irNode)
        is AEnumSeqExpIR -> SequenceEnumerations.translate(irNode)
        else             -> throw UnsupportedTranslationException(irNode)
    }
}
