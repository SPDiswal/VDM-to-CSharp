package org.overture.codegen.vdm2cs.unit.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetDifferenceBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.Differences
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DifferenceTranslations : ExpressionTranslationRuleSpek<ASetDifferenceBinaryExpIR>(Differences)
{
    init
    {
        "a \\ b" describesThat
            (identifier("a", setOf(boolType)) except identifier("b", setOf(boolType))) becomes
            "a.Except(b).ToHashSet()"

        "a \\ (b \\ c)" describesThat
            (identifier("a", setOf(intType)) except
                (identifier("b", setOf(intType)) except identifier("c", setOf(intType)))) becomes
            "a.Except(b.Except(c).ToHashSet()).ToHashSet()"

        "(a \\ b) \\ c" describesThat
            ((identifier("a", setOf(intType)) except identifier("b", setOf(intType))) except
                identifier("c", setOf(intType))) becomes
            "(a.Except(b).ToHashSet()).Except(c).ToHashSet()"
    }
}
