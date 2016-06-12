package org.overture.codegen.vdm2cs.unit.expressions.unary.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.Cardinalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class CardinalityTranslations : ExpressionTranslationRuleSpek<ACardUnaryExpIR>(Cardinalities)
{
    init
    {
        "card { }" describesThat
            card(emptySetEnumeration(intType)) becomes
            "new HashSet<int>().Count"

        "card { 1, ..., 3 }" describesThat
            card(setRange(1.toLiteral, 3.toLiteral)) becomes
            "Enumerable.Range(1, 3).ToHashSet().Count"
    }
}
