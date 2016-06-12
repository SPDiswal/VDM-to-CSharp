package org.overture.codegen.vdm2cs.unit.expressions.collections.sequences

import org.overture.codegen.ir.expressions.ACompSeqExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.sequences.SequenceComprehensions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SequenceComprehensionTranslations : ExpressionTranslationRuleSpek<ACompSeqExpIR>(SequenceComprehensions)
{
    init
    {
        "[ 1 | x in set { } ]" describesThat
            sequenceComprehension(binding = setBinding(pattern = identifierPattern("x"),
                                                       set = emptySetEnumeration(intType)),
                                  projection = 1.toLiteral) becomes
            "(from x in new HashSet<int>() " +
            " select 1).ToList()"

        "[ 2 * a | a in set { 1, ..., 3 } ]" describesThat
            sequenceComprehension(binding = setBinding(pattern = identifierPattern("a"),
                                                       set = setRange(1.toLiteral, 3.toLiteral)),
                                  projection = 2.toLiteral times identifier("a", intType)) becomes
            "(from a in Enumerable.Range(1, 3).ToHashSet() " +
            " select 2 * a).ToList()"

        "[ z + 1 | z in set { 1, ..., 3 } & z > 1]" describesThat
            sequenceComprehension(binding = setBinding(pattern = identifierPattern("z"),
                                                       set = setRange(1.toLiteral, 3.toLiteral)),
                                  predicate = identifier("z", intType) isGreaterThan 1.toLiteral,
                                  projection = identifier("z", intType) plus 1.toLiteral) becomes
            "(from z in Enumerable.Range(1, 3).ToHashSet() " +
            " where z > 1 " +
            " select z + 1).ToList()"

        "[ a | a in set { 1, ..., 6 } & a = 4 ]" describesThat
            sequenceComprehension(binding = setBinding(pattern = identifierPattern("a"),
                                                       set = setRange(1.toLiteral, 6.toLiteral)),
                                  predicate = (identifier("a", intType)) isEqualTo 4.toLiteral,
                                  projection = identifier("a", intType)) becomes
            "(from a in Enumerable.Range(1, 6).ToHashSet() " +
            " where a == 4 " +
            " select a).ToList()"
    }
}
