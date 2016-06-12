package org.overture.codegen.vdm2cs.unit.expressions.primary

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.primary.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ItemLookupTranslations : ExpressionTranslationRuleSpek<AFieldNumberExpIR>(ItemLookups)
{
    init
    {
        "$nextLowerCasePlaceholder.#1" describesThat
            (identifier(lowerCasePlaceholder, tupleOf(intType, intType, intType)) item 1) becomes
            "$lowerCasePlaceholder.Item1"

        "$nextLowerCasePlaceholder.#2" describesThat
            (identifier(lowerCasePlaceholder, tupleOf(intType, intType, intType)) item 2) becomes
            "$lowerCasePlaceholder.Item2"
    }
}
