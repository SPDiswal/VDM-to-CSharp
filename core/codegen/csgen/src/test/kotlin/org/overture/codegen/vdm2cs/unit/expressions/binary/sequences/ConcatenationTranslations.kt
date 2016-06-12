package org.overture.codegen.vdm2cs.unit.expressions.binary.sequences

import org.overture.codegen.ir.expressions.ASeqConcatBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.sequences.Concatenations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ConcatenationTranslations : ExpressionTranslationRuleSpek<ASeqConcatBinaryExpIR>(Concatenations)
{
    init
    {
        "a ^ b" describesThat
            (identifier("a", seqOf(boolType)) concatenate identifier("b", seqOf(boolType))) becomes
            "a.Concat(b).ToList()"

        "a ^ (b ^ c)" describesThat
            (identifier("a", seqOf(intType)) concatenate
                (identifier("b", seqOf(intType)) concatenate identifier("c", seqOf(intType)))) becomes
            "a.Concat(b.Concat(c).ToList()).ToList()"

        "(a ^ b) ^ c" describesThat
            ((identifier("a", seqOf(intType)) concatenate identifier("b", seqOf(intType))) concatenate
                identifier("c", seqOf(intType))) becomes
            "(a.Concat(b).ToList()).Concat(c).ToList()"

        "\"Hello, \" ^ \"World!\"" describesThat
            ("Hello, ".toLiteral concatenate "World!".toLiteral) becomes
            "\"Hello, \" + \"World!\""
    }
}
