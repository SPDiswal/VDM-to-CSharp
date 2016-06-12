package org.overture.codegen.vdm2cs.unit.expressions.collections.sets

import org.overture.codegen.ir.expressions.ACompSetExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.sets.SetComprehensions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SetComprehensionTranslations : ExpressionTranslationRuleSpek<ACompSetExpIR>(SetComprehensions)
{
    init
    {
        "{ 1 | x in set { } }" describesThat
            setComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("x")),
                                                          set = emptySetEnumeration(intType))),
                             projection = 1.toLiteral) becomes
            "(from x in new HashSet<int>() " +
            " select 1).ToHashSet()"

        "{ 2 * a | a in set { 1, ..., 3 } }" describesThat
            setComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("a")),
                                                          set = setRange(1.toLiteral, 3.toLiteral))),
                             projection = 2.toLiteral times identifier("a", intType)) becomes
            "(from a in Enumerable.Range(1, 3).ToHashSet() " +
            " select 2 * a).ToHashSet()"

        "{ z + 1 | z in set { 1, ..., 3 } & z > 1}" describesThat
            setComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("z")),
                                                          set = setRange(1.toLiteral, 3.toLiteral))),
                             predicate = identifier("z", intType) isGreaterThan 1.toLiteral,
                             projection = identifier("z", intType) plus 1.toLiteral) becomes
            "(from z in Enumerable.Range(1, 3).ToHashSet() " +
            " where z > 1 " +
            " select z + 1).ToHashSet()"

        "{ a - b | a, b in set { 1, ..., 6 } & a + b = 4 }" describesThat
            setComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("a"),
                                                                            identifierPattern("b")),
                                                          set = setRange(1.toLiteral, 6.toLiteral))),
                             predicate = (identifier("a", intType) plus identifier("b", intType)) isEqualTo 4.toLiteral,
                             projection = identifier("a", intType) minus identifier("b", intType)) becomes
            "(from a in Enumerable.Range(1, 6).ToHashSet() " +
            " from b in Enumerable.Range(1, 6).ToHashSet() " +
            " where a + b == 4 " +
            " select a - b).ToHashSet()"

        "{ true | x in set { 1, ..., 3 }, y in set { 1, ..., 6 } }" describesThat
            setComprehension(bindings = listOf(setBinding(patterns = listOf(identifierPattern("x")),
                                                          set = setRange(1.toLiteral, 3.toLiteral)),
                                               setBinding(patterns = listOf(identifierPattern("y")),
                                                          set = setRange(1.toLiteral, 6.toLiteral))),
                             projection = true.toLiteral) becomes
            "(from x in Enumerable.Range(1, 3).ToHashSet() " +
            " from y in Enumerable.Range(1, 6).ToHashSet() " +
            " select true).ToHashSet()"
    }
}
