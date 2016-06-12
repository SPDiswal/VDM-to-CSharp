package org.overture.codegen.vdm2cs.unit.expressions.binary.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ProperSubsetTranslations : ExpressionTranslationRuleSpek<ASetProperSubsetBinaryExpIR>(ProperSubsets)
{
    init
    {
        "a psubset { 1, ..., 3 }" describesThat
            (identifier("a", setOf(intType)) isProperSubsetOf setRange(1.toLiteral, 3.toLiteral)) becomes
            "a.IsProperSubsetOf(Enumerable.Range(1, 3).ToHashSet())"

        "{ 1, ..., 6 } psubset b" describesThat
            (setRange(1.toLiteral, 6.toLiteral) isProperSubsetOf identifier("b", setOf(intType))) becomes
            "Enumerable.Range(1, 6).ToHashSet().IsProperSubsetOf(b)"
    }
}
