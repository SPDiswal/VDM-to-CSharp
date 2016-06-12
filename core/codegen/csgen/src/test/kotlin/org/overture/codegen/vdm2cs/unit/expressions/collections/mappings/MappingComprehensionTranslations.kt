package org.overture.codegen.vdm2cs.unit.expressions.collections.mappings

import org.overture.codegen.ir.expressions.ACompMapExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.mappings.MappingComprehensions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MappingComprehensionTranslations : ExpressionTranslationRuleSpek<ACompMapExpIR>(MappingComprehensions)
{
    init
    {
        "{ 1 |-> 2 | x in set { } }" describesThat
            mappingComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("x")),
                                                              set = emptySetEnumeration(intType))),
                                 projection = maplet(from = 1.toLiteral, to = 2.toLiteral)) becomes
            "(from x in new HashSet<int>() " +
            " select new KeyValuePair<int, int>(1, 2)).ToDictionary()"

        "{ a |-> 2 * a | a in set { 1, ..., 3 } }" describesThat
            mappingComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("a")),
                                                              set = setRange(1.toLiteral, 3.toLiteral))),
                                 projection = maplet(from = identifier("a", intType),
                                                     to = 2.toLiteral times identifier("a", intType))) becomes
            "(from a in Enumerable.Range(1, 3).ToHashSet() " +
            " select new KeyValuePair<int, int>(a, 2 * a)).ToDictionary()"

        "{ z - 2 |-> \"Hello\" | z in set { 1, ..., 3 } & z > 1}" describesThat
            mappingComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("z")),
                                                              set = setRange(1.toLiteral, 3.toLiteral))),
                                 predicate = identifier("z", intType) isGreaterThan 1.toLiteral,
                                 projection = maplet(from = identifier("z", intType) minus 2.toLiteral,
                                                     to = "Hello".toLiteral)) becomes
            "(from z in Enumerable.Range(1, 3).ToHashSet() " +
            " where z > 1 " +
            " select new KeyValuePair<int, string>(z - 2, \"Hello\")).ToDictionary()"

        "{ a - b |-> a + b | a, b in set { 1, ..., 6 } & a + b = 4 }" describesThat
            mappingComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("a"),
                                                                                identifierPattern("b")),
                                                              set = setRange(1.toLiteral, 6.toLiteral))),
                                 predicate = (identifier("a", intType) plus identifier("b",
                                                                                       intType)) isEqualTo 4.toLiteral,
                                 projection = maplet(from = identifier("a", intType) minus identifier("b", intType),
                                                     to = identifier("a", intType) plus
                                                         identifier("b", intType))) becomes
            "(from a in Enumerable.Range(1, 6).ToHashSet() " +
            " from b in Enumerable.Range(1, 6).ToHashSet() " +
            " where a + b == 4 " +
            " select new KeyValuePair<int, int>(a - b, a + b)).ToDictionary()"

        "{ x |-> y | x in set { 1, ..., 3 }, y in set { 1, ..., 6 } }" describesThat
            mappingComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("x")),
                                                              set = setRange(1.toLiteral, 3.toLiteral)),
                                                   setBinding(patterns = listOf(identifierPattern("y")),
                                                              set = setRange(1.toLiteral, 6.toLiteral))),
                                 projection = maplet(from = identifier("x", intType),
                                                     to = identifier("y", intType))) becomes
            "(from x in Enumerable.Range(1, 3).ToHashSet() " +
            " from y in Enumerable.Range(1, 6).ToHashSet() " +
            " select new KeyValuePair<int, int>(x, y)).ToDictionary()"
    }
}
