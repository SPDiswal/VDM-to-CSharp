package org.overture.codegen.vdm2cs.unit.expressions.unary.sets

import org.overture.codegen.ir.expressions.ADistIntersectUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.DistributedIntersections
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DistributedIntersectionTranslations : ExpressionTranslationRuleSpek<ADistIntersectUnaryExpIR>(
    DistributedIntersections)
{
    init
    {
        "dinter { { }, { \"ABC\" } }" describesThat
            distributedIntersection(setEnumeration(
                emptySetEnumeration(seqOf(charType)),
                setEnumeration("ABC".toLiteral)
            )) becomes
            "(new HashSet<HashSet<string>> { new HashSet<string>()," +
            "                             new HashSet<string> { \"ABC\" } })" +
            ".Cast<IEnumerable<string>>()" +
            ".Aggregate(Enumerable.Intersect)" +
            ".ToHashSet()"

        "dinter { { 1, ..., 3 }, { 1, ..., 6 } }" describesThat
            distributedIntersection(setEnumeration(
                setRange(1.toLiteral, 3.toLiteral),
                setRange(1.toLiteral, 6.toLiteral)
            )) becomes
            "(new HashSet<HashSet<int>> { Enumerable.Range(1, 3).ToHashSet()," +
            "                          Enumerable.Range(1, 6).ToHashSet() })" +
            ".Cast<IEnumerable<int>>()" +
            ".Aggregate(Enumerable.Intersect)" +
            ".ToHashSet()"
    }
}
