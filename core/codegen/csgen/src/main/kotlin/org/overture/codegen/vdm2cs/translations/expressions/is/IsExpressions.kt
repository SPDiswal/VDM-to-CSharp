package org.overture.codegen.vdm2cs.translations.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object IsExpressions : ExpressionTranslationRule<SIsExpIR>
{
    override fun translate(irNode: SIsExpIR) = when (irNode)
    {
        is ABoolIsExpIR    -> IsBools.translate(irNode)
        is ACharIsExpIR    -> IsChars.translate(irNode)
        is AIntIsExpIR     -> IsInts.translate(irNode)
        is ANatIsExpIR     -> IsNats.translate(irNode)
        is ANat1IsExpIR    -> IsNat1s.translate(irNode)
        is ARatIsExpIR     -> IsRats.translate(irNode)
        is ARealIsExpIR    -> IsReals.translate(irNode)
        is ATupleIsExpIR   -> IsTuples.translate(irNode)
        is AGeneralIsExpIR -> CommonIsExpressions.translate(irNode)
        else               -> throw UnsupportedTranslationException(irNode)
    }
}
