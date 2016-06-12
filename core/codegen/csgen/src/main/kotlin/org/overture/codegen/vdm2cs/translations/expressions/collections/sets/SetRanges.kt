package org.overture.codegen.vdm2cs.translations.expressions.collections.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SetRanges : ExpressionTranslationRule<ARangeSetExpIR>
{
    override fun translate(irNode: ARangeSetExpIR): CsExpression
    {
        val lowerBound = irNode.first
        val upperBound = irNode.last

        return when
        {
            lowerBound is SLiteralExpIR && upperBound is SLiteralExpIR ->
            {
                val rangeLowerBound = lowerBound.lowerBoundValue
                val rangeUpperBound = (upperBound.upperBoundValue - lowerBound.lowerBoundValue + 1L).coerceAtLeast(0L)

                when (rangeUpperBound)
                {
                    0L   -> parseExpression("Enumerable.Empty<int>().ToHashSet()")
                    else -> parseExpression("Enumerable.Range($rangeLowerBound, $rangeUpperBound).ToHashSet()")
                }
            }
            else                                                       ->
            {
                val rangeLowerBound = lowerBound.inline
                val rangeUpperBound = "Math.Max((${upperBound.inline}) - (${lowerBound.inline}) + 1, 0)"

                parseExpression("Enumerable.Range($rangeLowerBound, $rangeUpperBound).ToHashSet()")
            }
        }
    }
}
