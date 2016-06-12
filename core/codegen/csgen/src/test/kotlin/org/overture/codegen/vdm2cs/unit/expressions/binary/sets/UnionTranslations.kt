package org.overture.codegen.vdm2cs.unit.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetUnionBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.Unions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class UnionTranslations : ExpressionTranslationRuleSpek<ASetUnionBinaryExpIR>(Unions)
{
    init
    {
        "a union b" describesThat
            (identifier("a", setOf(boolType)) union identifier("b", setOf(boolType))) becomes
            "a.Union(b).ToHashSet()"

        "a union (b union c)" describesThat
            (identifier("a", setOf(intType)) union
                (identifier("b", setOf(intType)) union identifier("c", setOf(intType)))) becomes
            "a.Union(b.Union(c).ToHashSet()).ToHashSet()"

        "(a union b) union c" describesThat
            ((identifier("a", setOf(intType)) union identifier("b", setOf(intType))) union
                identifier("c", setOf(intType))) becomes
            "(a.Union(b).ToHashSet()).Union(c).ToHashSet()"
    }
}
