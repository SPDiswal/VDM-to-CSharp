package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.AUnionTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.UnionTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class UnionTypeTranslations : TypeTranslationRuleSpek<AUnionTypeIR>(UnionTypes)
{
    init
    {
        "bool | nat" describesThat
            unionOf(boolType, natType) becomes "object"

        "int | char | seq of nat" describesThat
            unionOf(intType, charType, seqOf(natType)) becomes "object"

        "(bool | nat) | (char | seq of char)" describesThat
            unionOf(unionOf(boolType, natType), unionOf(charType, seqOf(charType))) becomes
            "object"

        "<FirstQuote> | <SecondQuote>" describesThat
            unionOf(quoteType("FirstQuote"), quoteType("SecondQuote")) becomes
            "Quote"

        "<firstQuote> | <SecondQuote> | <thirdQuote>" describesThat
            unionOf(quoteType("firstQuote"), quoteType("SecondQuote"), quoteType("thirdQuote")) becomes
            "Quote"
    }
}
