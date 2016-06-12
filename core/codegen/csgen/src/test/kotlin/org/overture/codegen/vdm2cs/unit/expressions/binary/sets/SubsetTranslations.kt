package org.overture.codegen.vdm2cs.unit.expressions.binary.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SubsetTranslations : ExpressionTranslationRuleSpek<ASetSubsetBinaryExpIR>(Subsets)
{
    init
    {
        "a subset { 1, ..., 3 }" describesThat
            (identifier("a", setOf(intType)) isSubsetOf setRange(1.toLiteral, 3.toLiteral)) becomes
            "a.IsSubsetOf(Enumerable.Range(1, 3).ToHashSet())"

        "{ 1, ..., 6 } subset b" describesThat
            (setRange(1.toLiteral, 6.toLiteral) isSubsetOf identifier("b", setOf(intType))) becomes
            "Enumerable.Range(1, 6).ToHashSet().IsSubsetOf(b)"
    }
}
