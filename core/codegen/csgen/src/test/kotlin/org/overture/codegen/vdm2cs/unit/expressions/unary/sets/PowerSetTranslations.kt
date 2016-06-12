package org.overture.codegen.vdm2cs.unit.expressions.unary.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class PowerSetTranslations : ExpressionTranslationRuleSpek<APowerSetUnaryExpIR>(PowerSets)
{
    init
    {
        "power { }" describesThat
            powerSet(emptySetEnumeration(intType)) becomes
            "new HashSet<int>().PowerSet()"

        "power { 1, ..., 3 }" describesThat
            powerSet(setRange(1.toLiteral, 3.toLiteral)) becomes
            "Enumerable.Range(1, 3).ToHashSet().PowerSet()"
    }
}
