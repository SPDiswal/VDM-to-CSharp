package org.overture.codegen.vdm2cs.unit.expressions.unary.sets

import org.overture.codegen.ir.expressions.ADistUnionUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.DistributedUnions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DistributedUnionTranslations : ExpressionTranslationRuleSpek<ADistUnionUnaryExpIR>(DistributedUnions)
{
    init
    {
        "dunion { { }, { \"ABC\" } }" describesThat
            distributedUnion(setEnumeration(
                emptySetEnumeration(seqOf(charType)),
                setEnumeration("ABC".toLiteral)
            )) becomes
            "(new HashSet<HashSet<string>> { new HashSet<string>()," +
            "                             new HashSet<string> { \"ABC\" } })" +
            ".Cast<IEnumerable<string>>()" +
            ".Aggregate(Enumerable.Union)" +
            ".ToHashSet()"

        "dunion { { 1, ..., 3 }, { 1, ..., 6 } }" describesThat
            distributedUnion(setEnumeration(
                setRange(1.toLiteral, 3.toLiteral),
                setRange(1.toLiteral, 6.toLiteral)
            )) becomes
            "(new HashSet<HashSet<int>> { Enumerable.Range(1, 3).ToHashSet()," +
            "                          Enumerable.Range(1, 6).ToHashSet() })" +
            ".Cast<IEnumerable<int>>()" +
            ".Aggregate(Enumerable.Union)" +
            ".ToHashSet()"
    }
}
