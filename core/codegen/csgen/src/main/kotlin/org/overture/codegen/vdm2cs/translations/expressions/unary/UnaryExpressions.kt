package org.overture.codegen.vdm2cs.translations.expressions.unary

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.unary.arithmetic.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.mappings.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.*

object UnaryExpressions : UnaryExpressionTranslationRule<SUnaryExpIR>
{
    override fun translate(irNode: SUnaryExpIR) = when (irNode)
    {
    // ARITHMETIC
        is APlusUnaryExpIR          -> UnaryPlusExpressions.translate(irNode)
        is AMinusUnaryExpIR         -> UnaryMinusExpressions.translate(irNode)
        is AAbsUnaryExpIR           -> AbsoluteValues.translate(irNode)
        is AFloorUnaryExpIR         -> Floors.translate(irNode)

    // LOGICAL
        is ANotUnaryExpIR           -> Negations.translate(irNode)

    // SETS
        is ACardUnaryExpIR          -> Cardinalities.translate(irNode)
        is ADistUnionUnaryExpIR     -> DistributedUnions.translate(irNode)
        is ADistIntersectUnaryExpIR -> DistributedIntersections.translate(irNode)
        is APowerSetUnaryExpIR      -> PowerSets.translate(irNode)

    // SEQUENCES
        is ALenUnaryExpIR           -> Lengths.translate(irNode)
        is AElemsUnaryExpIR         -> Elements.translate(irNode)
        is AIndicesUnaryExpIR       -> Indices.translate(irNode)
        is AHeadUnaryExpIR          -> Heads.translate(irNode)
        is ATailUnaryExpIR          -> Tails.translate(irNode)
        is ADistConcatUnaryExpIR    -> DistributedConcatenations.translate(irNode)

    // MAPPINGS
        is AMapDomainUnaryExpIR     -> Domains.translate(irNode)
        is AMapRangeUnaryExpIR      -> Ranges.translate(irNode)
        is ADistMergeUnaryExpIR     -> DistributedMerges.translate(irNode)

    // OTHER
        is AIsolationUnaryExpIR     -> Isolations.translate(irNode)

        else                        -> throw UnsupportedTranslationException(irNode)
    }
}
