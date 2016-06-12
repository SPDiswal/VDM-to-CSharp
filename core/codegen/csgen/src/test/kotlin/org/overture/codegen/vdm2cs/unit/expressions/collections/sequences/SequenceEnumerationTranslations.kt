package org.overture.codegen.vdm2cs.unit.expressions.collections.sequences

import org.overture.codegen.ir.expressions.AEnumSeqExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.sequences.SequenceEnumerations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SequenceEnumerationTranslations : ExpressionTranslationRuleSpek<AEnumSeqExpIR>(SequenceEnumerations)
{
    init
    {
        "[ ]: seq of int" describesThat
            emptySequenceEnumeration(intType) becomes
            "new List<int>()"

        "[ ]: seq of seq of char" describesThat
            emptySequenceEnumeration(seqOf(charType)) becomes
            "new List<string>()"

        "[ 1, 2, 3 ]: seq of int" describesThat
            sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral) becomes
            "new List<int> { 1, 2, 3 }"

        "[ \"Hello\", \"World\" ]: seq of seq of char" describesThat
            sequenceEnumeration("Hello".toLiteral, "World".toLiteral) becomes
            "new List<string> { \"Hello\", \"World\" }"

        "[ [ ], [ 1, 2 ] ]: seq of seq of int" describesThat
            sequenceEnumeration(emptySequenceEnumeration(intType),
                                sequenceEnumeration(1.toLiteral, 2.toLiteral)) becomes
            "new List<List<int>> { new List<int>(), new List<int> { 1, 2 } }"

        "[ 'A', 'B', 'C' ]: seq of int" describesThat
            sequenceEnumeration('A'.toLiteral, 'B'.toLiteral, 'C'.toLiteral) becomes
            "\"ABC\""
    }
}
