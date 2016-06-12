package org.overture.codegen.vdm2cs.unit.expressions.collections.sets

import org.overture.codegen.ir.expressions.ARangeSetExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.sets.SetRanges
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SetRangeTranslations : ExpressionTranslationRuleSpek<ARangeSetExpIR>(SetRanges)
{
    init
    {
        "{ 1, ..., 6 }" describesThat
            setRange(1.toLiteral, 6.toLiteral) becomes
            "Enumerable.Range(1, 6).ToHashSet()"

        "{ 3, ..., 10 }" describesThat
            setRange(3.toLiteral, 10.toLiteral) becomes
            "Enumerable.Range(3, 8).ToHashSet()"

        "{ -3, ..., 3 }" describesThat
            setRange((-3).toLiteral, 3.toLiteral) becomes
            "Enumerable.Range(-3, 7).ToHashSet()"

        "{ 2, ..., 1 }" describesThat
            setRange(2.toLiteral, 1.toLiteral) becomes
            "Enumerable.Empty<int>().ToHashSet()"

        "{ 1.5, ..., 4.5 }" describesThat
            setRange(1.5.toLiteral, 4.5.toLiteral) becomes
            "Enumerable.Range(2, 3).ToHashSet()"

        "{ -2.5, ..., 2.5 }" describesThat
            setRange((-2.5).toLiteral, 2.5.toLiteral) becomes
            "Enumerable.Range(-2, 5).ToHashSet()"

        "{ 0.5, ..., -0.5 }" describesThat
            setRange(0.5.toLiteral, (-0.5).toLiteral) becomes
            "Enumerable.Empty<int>().ToHashSet()"

        "{ a, ..., b }" describesThat
            setRange(identifier("a", intType), identifier("b", intType)) becomes
            "Enumerable.Range(a, Math.Max(b - a + 1, 0)).ToHashSet()"

        "{ a + b, ..., c - d }" describesThat
            setRange(identifier("a", intType) plus identifier("b", intType),
                     identifier("c", intType) minus identifier("d", intType)) becomes
            "Enumerable.Range(a + b, Math.Max((c - d) - (a + b) + 1, 0)).ToHashSet()"
    }
}
