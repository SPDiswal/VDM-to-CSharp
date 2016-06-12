package org.overture.codegen.vdm2cs.translations.expressions.collections.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SubSequences : ExpressionTranslationRule<ASubSeqExpIR>
{
    override fun translate(irNode: ASubSeqExpIR): CsExpression
    {
        val sequence = irNode.seq.inline
        val lowerBound = irNode.from
        val upperBound = irNode.to

        return when
        {
            lowerBound is SLiteralExpIR && upperBound is SLiteralExpIR ->
            {
                val rangeLowerBound = (lowerBound.lowerBoundValue - 1L).coerceAtLeast(0L)
                val rangeUpperBound = (upperBound.upperBoundValue - lowerBound.lowerBoundValue + 1L).coerceAtLeast(0L)

                when
                {
                    rangeLowerBound == 0L -> parseExpression("$sequence.Take($rangeUpperBound).ToList()")
                    rangeUpperBound == 0L -> parseExpression("Enumerable.Empty<int>().ToList()")
                    else                  ->
                        parseExpression("$sequence.Skip($rangeLowerBound).Take($rangeUpperBound).ToList()")
                }
            }
            else                                                       ->
            {
                val rangeLowerBound = "(${lowerBound.inline}) - 1"
                val rangeUpperBound = "(${upperBound.inline}) - (${lowerBound.inline}) + 1"

                parseExpression("$sequence.Skip($rangeLowerBound).Take($rangeUpperBound).ToList()")
            }
        }
    }
}
