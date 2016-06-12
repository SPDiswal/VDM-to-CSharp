package org.overture.codegen.vdm2cs.unit.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetIntersectBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.Intersections
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IntersectionTranslations : ExpressionTranslationRuleSpek<ASetIntersectBinaryExpIR>(Intersections)
{
    init
    {
        "a inter b" describesThat
            (identifier("a", setOf(boolType)) intersect identifier("b", setOf(boolType))) becomes
            "a.Intersect(b).ToHashSet()"

        "a inter (b inter c)" describesThat
            (identifier("a", setOf(intType)) intersect
                (identifier("b", setOf(intType)) intersect identifier("c", setOf(intType)))) becomes
            "a.Intersect(b.Intersect(c).ToHashSet()).ToHashSet()"

        "(a inter b) inter c" describesThat
            ((identifier("a", setOf(intType)) intersect identifier("b", setOf(intType))) intersect
                identifier("c", setOf(intType))) becomes
            "(a.Intersect(b).ToHashSet()).Intersect(c).ToHashSet()"
    }
}
