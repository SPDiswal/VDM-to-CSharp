package org.overture.codegen.vdm2cs.unit.expressions.binary.mappings

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.mappings.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.sets.Unions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class OverrideTranslations : ExpressionTranslationRuleSpek<AMapOverrideBinaryExpIR>(Overrides)
{
    init
    {
        "a ++ b" describesThat
            (identifier("a", map(boolType to boolType)) override
                identifier("b", map(boolType to boolType))) becomes
            "a.OverrideBy(b).ToDictionary()"

        "a ++ (b ++ c)" describesThat
            (identifier("a", map(intType to intType)) override
                (identifier("b", map(intType to intType)) override
                    identifier("c", map(intType to intType)))) becomes
            "a.OverrideBy(b.OverrideBy(c).ToDictionary()).ToDictionary()"

        "(a ++ b) ++ c" describesThat
            ((identifier("a", map(intType to intType)) override
                identifier("b", map(intType to intType))) override
                identifier("c", map(intType to intType))) becomes
            "(a.OverrideBy(b).ToDictionary()).OverrideBy(c).ToDictionary()"
    }
}
