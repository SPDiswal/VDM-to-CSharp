package org.overture.codegen.vdm2cs.unit.expressions.collections.sequences

import org.overture.codegen.ir.expressions.ASubSeqExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.sequences.SubSequences
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SubSequenceTranslations : ExpressionTranslationRuleSpek<ASubSeqExpIR>(SubSequences)
{
    init
    {
        "[ 1, 2, 3, 4, 5 ](1, ..., 3)" describesThat
            subSequence(sequence = sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral, 4.toLiteral, 5.toLiteral),
                        lowerIndexInclusive = 1.toLiteral,
                        upperIndexInclusive = 3.toLiteral) becomes
            "new List<int> { 1, 2, 3, 4, 5 }.Take(3).ToList()"

        "[ 1, 2, 3, 4, 5 ](1, ..., 3)" describesThat
            subSequence(sequence = sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral, 4.toLiteral, 5.toLiteral),
                        lowerIndexInclusive = 1.toLiteral,
                        upperIndexInclusive = 5.toLiteral) becomes
            "new List<int> { 1, 2, 3, 4, 5 }.Take(5).ToList()"

        "[ 1, 2, 3, 4, 5 ](3, ..., 5)" describesThat
            subSequence(sequence = sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral, 4.toLiteral, 5.toLiteral),
                        lowerIndexInclusive = 3.toLiteral,
                        upperIndexInclusive = 5.toLiteral) becomes
            "new List<int> { 1, 2, 3, 4, 5 }.Skip(2).Take(3).ToList()"

        "[ 1, 2, 3, 4, 5 ](3, ..., 3)" describesThat
            subSequence(sequence = sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral, 4.toLiteral, 5.toLiteral),
                        lowerIndexInclusive = 3.toLiteral,
                        upperIndexInclusive = 3.toLiteral) becomes
            "new List<int> { 1, 2, 3, 4, 5 }.Skip(2).Take(1).ToList()"

        "[ 1, 2, 3, 4, 5 ](0, ..., 7)" describesThat
            subSequence(sequence = sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral, 4.toLiteral, 5.toLiteral),
                        lowerIndexInclusive = 0.toLiteral,
                        upperIndexInclusive = 7.toLiteral) becomes
            "new List<int> { 1, 2, 3, 4, 5 }.Take(8).ToList()"

        "[ 1, 2, 3, 4, 5 ](a, ..., b)" describesThat
            subSequence(sequence = sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral, 4.toLiteral, 5.toLiteral),
                        lowerIndexInclusive = identifier("a", intType),
                        upperIndexInclusive = identifier("b", intType)) becomes
            "new List<int> { 1, 2, 3, 4, 5 }.Skip(a - 1).Take(b - a + 1).ToList()"
    }
}
