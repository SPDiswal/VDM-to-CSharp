package org.overture.codegen.vdm2cs.unit.expressions.unary.mappings

import org.overture.codegen.ir.expressions.ADistMergeUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.mappings.DistributedMerges
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DistributedMergeTranslations : ExpressionTranslationRuleSpek<ADistMergeUnaryExpIR>(DistributedMerges)
{
    init
    {
        "merge { { |-> }, { 1 |-> 2 } }" describesThat
            distributedMerge(setEnumeration(
                emptyMappingEnumeration(from = intType, to = intType),
                mappingEnumeration(1.toLiteral to 2.toLiteral)
            )) becomes
            "(new HashSet<Dictionary<int, int>> { new Dictionary<int, int>()," +
            "                                      new Dictionary<int, int> { [1] = 2 } })" +
            ".Cast<IEnumerable<KeyValuePair<int, int>>>()" +
            ".Aggregate(Enumerable.Concat)" +
            ".ToDictionary()"

        "merge { { 1 |-> 2 }, { 2 |-> 3 }, { 3 |-> 4, 4 |-> 5 } }" describesThat
            distributedMerge(setEnumeration(
                mappingEnumeration(1.toLiteral to 2.toLiteral),
                mappingEnumeration(2.toLiteral to 3.toLiteral),
                mappingEnumeration(3.toLiteral to 4.toLiteral, 4.toLiteral to 5.toLiteral)
            )) becomes
            "(new HashSet<Dictionary<int, int>> { new Dictionary<int, int> { [1] = 2 }," +
            "                                      new Dictionary<int, int> { [2] = 3 }," +
            "                                      new Dictionary<int, int> { [3] = 4, [4] = 5 } })" +
            ".Cast<IEnumerable<KeyValuePair<int, int>>>()" +
            ".Aggregate(Enumerable.Concat)" +
            ".ToDictionary()"
    }
}
