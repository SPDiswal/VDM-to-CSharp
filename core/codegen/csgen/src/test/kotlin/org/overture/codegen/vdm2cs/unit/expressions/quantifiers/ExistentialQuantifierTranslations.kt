package org.overture.codegen.vdm2cs.unit.expressions.quantifiers

import org.overture.codegen.ir.expressions.AExistsQuantifierExpIR
import org.overture.codegen.vdm2cs.translations.expressions.quantifiers.ExistentialQuantifiers
import org.overture.codegen.vdm2cs.utilities.*

class ExistentialQuantifierTranslations : QuantifierTranslationRuleSpek<AExistsQuantifierExpIR>(ExistentialQuantifiers)
{
    init
    {
        "exists a in set { 1, ..., 3 } & a >= 1" describesThat
            existentialQuantifier(bindings = listOf(setBinding(patterns = listOf(identifierPattern("a")),
                                                               set = setRange(1.toLiteral, 3.toLiteral))),
                                  predicate = identifier("a", intType) isGreaterThanOrEqualTo 1.toLiteral) becomes
            "(from a in Enumerable.Range(1, 3).ToHashSet() " +
            " select a >= 1).Any(_ => _)"

        "exists a, b in set { 1, ..., 6 } & a + b > 4" describesThat
            existentialQuantifier(bindings = listOf(setBinding(patterns = listOf(identifierPattern("a"),
                                                                                 identifierPattern("b")),
                                                               set = setRange(1.toLiteral, 6.toLiteral))),
                                  predicate = (identifier("a", intType) plus
                                      identifier("b", intType)) isGreaterThan 4.toLiteral) becomes
            "(from a in Enumerable.Range(1, 6).ToHashSet() " +
            " from b in Enumerable.Range(1, 6).ToHashSet() " +
            " select a + b > 4).Any(_ => _)"

        "exists x in set { 1, ..., 2 }, y, z in set { 1, ..., 4 } & x + y + z = 5" describesThat
            existentialQuantifier(bindings = listOf(setBinding(patterns = listOf(identifierPattern("x")),
                                                               set = setRange(1.toLiteral, 2.toLiteral)),
                                                    setBinding(patterns = listOf(identifierPattern("y"),
                                                                                 identifierPattern("z")),
                                                               set = setRange(1.toLiteral, 4.toLiteral))),
                                  predicate = (identifier("x", intType) plus identifier("y", intType) plus
                                      identifier("z", intType)) isEqualTo 5.toLiteral) becomes
            "(from x in Enumerable.Range(1, 2).ToHashSet() " +
            " from y in Enumerable.Range(1, 4).ToHashSet() " +
            " from z in Enumerable.Range(1, 4).ToHashSet() " +
            " select x + y + z == 5).Any(_ => _)"
    }
}
