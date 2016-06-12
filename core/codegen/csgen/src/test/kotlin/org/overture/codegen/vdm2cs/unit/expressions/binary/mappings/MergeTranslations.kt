package org.overture.codegen.vdm2cs.unit.expressions.binary.mappings

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.mappings.Merges
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.Unions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MergeTranslations : ExpressionTranslationRuleSpek<AMapUnionBinaryExpIR>(Merges)
{
    init
    {
        "a munion b" describesThat
            (identifier("a", map(boolType to boolType)) merge
                identifier("b", map(boolType to boolType))) becomes
            "a.Concat(b).ToDictionary()"

        "a munion (b munion c)" describesThat
            (identifier("a", map(intType to intType)) merge
                (identifier("b", map(intType to intType)) merge
                    identifier("c", map(intType to intType)))) becomes
            "a.Concat(b.Concat(c).ToDictionary()).ToDictionary()"

        "(a munion b) munion c" describesThat
            ((identifier("a", map(intType to intType)) merge
                identifier("b", map(intType to intType))) merge
                identifier("c", map(intType to intType))) becomes
            "(a.Concat(b).ToDictionary()).Concat(c).ToDictionary()"
    }
}
