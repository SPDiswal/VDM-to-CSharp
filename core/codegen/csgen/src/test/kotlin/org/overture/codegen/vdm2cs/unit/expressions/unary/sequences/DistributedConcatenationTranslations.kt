package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.ADistConcatUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.DistributedConcatenations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DistributedConcatenationTranslations : ExpressionTranslationRuleSpek<ADistConcatUnaryExpIR>(
    DistributedConcatenations)
{
    init
    {
        "conc [ [ ], [ 1, 2 ] ]" describesThat
            distributedConcatenation(sequenceEnumeration(
                emptySequenceEnumeration(intType),
                sequenceEnumeration(1.toLiteral, 2.toLiteral)
            )) becomes
            "(new List<List<int>> { new List<int>()," +
            "                        new List<int> { 1, 2 } })" +
            ".Cast<IEnumerable<int>>()" +
            ".Aggregate(Enumerable.Concat)" +
            ".ToList()"

        "conc [ [ 1, 2, 3 ], [ 4, 5, 6 ] ]" describesThat
            distributedConcatenation(sequenceEnumeration(
                sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral),
                sequenceEnumeration(4.toLiteral, 5.toLiteral, 6.toLiteral)
            )) becomes
            "(new List<List<int>> { new List<int> { 1, 2, 3 }," +
            "                        new List<int> { 4, 5, 6 } })" +
            ".Cast<IEnumerable<int>>()" +
            ".Aggregate(Enumerable.Concat)" +
            ".ToList()"
    }
}
