package org.overture.codegen.vdm2cs.translations.expressions.binary

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.mappings.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.sequences.Concatenations
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.*

object BinaryExpressions : BinaryExpressionTranslationRule<SBinaryExpIR>
{
    override fun translate(irNode: SBinaryExpIR) = when (irNode)
    {
    // ARITHMETIC
        is APlusNumericBinaryExpIR         -> Additions.translate(irNode)
        is ASubtractNumericBinaryExpIR     -> Subtractions.translate(irNode)
        is ATimesNumericBinaryExpIR        -> Multiplications.translate(irNode)
        is ADivideNumericBinaryExpIR       -> Divisions.translate(irNode)
        is AIntDivNumericBinaryExpIR       -> IntegerDivisions.translate(irNode)
        is AModNumericBinaryExpIR          -> Modulus.translate(irNode)
        is ARemNumericBinaryExpIR          -> Remainders.translate(irNode)
        is APowerNumericBinaryExpIR        -> Powers.translate(irNode)

    // LOGICAL
        is AEqualsBinaryExpIR              -> Equalities.translate(irNode)
        is ANotEqualsBinaryExpIR           -> Inequalities.translate(irNode)
        is AGreaterNumericBinaryExpIR      -> GreaterThanInequalities.translate(irNode)
        is AGreaterEqualNumericBinaryExpIR -> GreaterThanOrEqualToInequalities.translate(irNode)
        is ALessNumericBinaryExpIR         -> LessThanInequalities.translate(irNode)
        is ALessEqualNumericBinaryExpIR    -> LessThanOrEqualToInequalities.translate(irNode)
        is AAndBoolBinaryExpIR             -> Conjunctions.translate(irNode)
        is AXorBoolBinaryExpIR             -> ExclusiveDisjunctions.translate(irNode)
        is AOrBoolBinaryExpIR              -> Disjunctions.translate(irNode)

    // SETS
        is ASetUnionBinaryExpIR            -> Unions.translate(irNode)
        is ASetIntersectBinaryExpIR        -> Intersections.translate(irNode)
        is ASetDifferenceBinaryExpIR       -> Differences.translate(irNode)
        is AInSetBinaryExpIR               -> Memberships.translate(irNode)
        is ASetSubsetBinaryExpIR           -> Subsets.translate(irNode)
        is ASetProperSubsetBinaryExpIR     -> ProperSubsets.translate(irNode)

    // SEQUENCES
        is ASeqConcatBinaryExpIR           -> Concatenations.translate(irNode)

    // MAPPINGS
        is AMapUnionBinaryExpIR            -> Merges.translate(irNode)
        is AMapOverrideBinaryExpIR         -> Overrides.translate(irNode)

        else                               -> throw UnsupportedTranslationException(irNode)
    }
}
