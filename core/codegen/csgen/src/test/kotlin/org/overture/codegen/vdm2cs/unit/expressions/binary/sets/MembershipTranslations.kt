package org.overture.codegen.vdm2cs.unit.expressions.binary.sets

import org.overture.codegen.ir.expressions.AInSetBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.Memberships
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MembershipTranslations : ExpressionTranslationRuleSpek<AInSetBinaryExpIR>(Memberships)
{
    init
    {
        "a in set { 1, ..., 3 }" describesThat
            (identifier("a", setOf(intType)) isMemberOf setRange(1.toLiteral, 3.toLiteral)) becomes
            "Enumerable.Range(1, 3).ToHashSet().Contains(a)"

        "{ 1, ..., 6 } in set b" describesThat
            (setRange(1.toLiteral, 6.toLiteral) isMemberOf identifier("b", setOf(setOf(intType)))) becomes
            "b.Contains(Enumerable.Range(1, 6).ToHashSet())"

        //region SEQUENCES

        "a in set elems b" describesThat
            (identifier("a", intType) isMemberOf elements(identifier("b", seqOf(intType)))) becomes
            "b.Contains(a)"

        //endregion

        //region MAPPINGS

        "a in set dom b" describesThat
            (identifier("a", setOf(intType)) isMemberOf
                domain(identifier("b", map(intType to intType)))) becomes
            "b.ContainsKey(a)"

        "a in set rng b" describesThat
            (identifier("a", setOf(intType)) isMemberOf
                range(identifier("b", map(intType to intType)))) becomes
            "b.ContainsValue(a)"

        //endregion
    }
}
