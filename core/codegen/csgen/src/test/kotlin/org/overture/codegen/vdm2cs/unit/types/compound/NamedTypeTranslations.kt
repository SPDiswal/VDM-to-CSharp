package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.vdm2cs.translations.types.Types
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class NamedTypeTranslations : TypeTranslationRuleSpek<STypeIR>(Types)
{
    init
    {
        "$nextPlaceholder = bool" describesThat
            namedType(name = placeholder, type = boolType) becomes
            placeholder

        "$nextLowerCasePlaceholder = char" describesThat
            namedType(name = lowerCasePlaceholder, type = charType) becomes
            placeholder

        "$$nextPlaceholder = int" describesThat
            namedType(name = placeholder, type = intType) becomes
            placeholder

        "$nextLowerCasePlaceholder = nat" describesThat
            namedType(name = lowerCasePlaceholder, type = natType) becomes
            placeholder

        "$$nextPlaceholder = nat1" describesThat
            namedType(name = placeholder, type = nat1Type) becomes
            placeholder

        "$nextLowerCasePlaceholder = rat" describesThat
            namedType(name = lowerCasePlaceholder, type = ratType) becomes
            placeholder

        "$$nextPlaceholder = real" describesThat
            namedType(name = placeholder, type = realType) becomes
            placeholder

        "$nextLowerCasePlaceholder = token" describesThat
            namedType(name = lowerCasePlaceholder, type = tokenType) becomes
            placeholder

        "$nextLowerCasePlaceholder = map bool to bool" describesThat
            namedType(name = lowerCasePlaceholder, type = map(boolType to boolType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = int * int" describesThat
            namedType(name = lowerCasePlaceholder, type = tupleOf(intType, intType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = seq of char" describesThat
            namedType(name = lowerCasePlaceholder, type = seqOf(charType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = seq of int" describesThat
            namedType(name = lowerCasePlaceholder, type = seqOf(intType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = set of int" describesThat
            namedType(name = lowerCasePlaceholder, type = setOf(intType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = int | char" describesThat
            namedType(name = lowerCasePlaceholder, type = unionOf(intType, charType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = int -> int" describesThat
            namedType(name = lowerCasePlaceholder, type = methodType(listOf(intType), intType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = Record" describesThat
            namedType(name = lowerCasePlaceholder, type = recordType(name = "Record")) becomes
            placeholder

        "$nextLowerCasePlaceholder = <Quote>" describesThat
            namedType(name = lowerCasePlaceholder, type = quoteType(name = "Quote")) becomes
            placeholder

        "$nextLowerCasePlaceholder = <FirstQuote> | <SecondQuote>" describesThat
            namedType(name = lowerCasePlaceholder, type = unionOf(quoteType(name = "FirstQuote"),
                                                                  quoteType(name = "SecondQuote"))) becomes
            placeholder

        "$nextLowerCasePlaceholder = [bool]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(boolType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [char]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(charType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [int]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(intType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [nat]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(natType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [nat1]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(nat1Type)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [rat]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(ratType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [real]" describesThat
            namedType(name = lowerCasePlaceholder, type = optional(realType)) becomes
            placeholder

        "$nextLowerCasePlaceholder = [<Quote>]" describesThat
            namedType(name = placeholder, type = optional(quoteType(name = "Quote"))) becomes
            placeholder

        "$nextLowerCasePlaceholder = [<FirstQuote> | <SecondQuote>]" describesThat
            namedType(name = placeholder, type = optional(unionOf(quoteType(name = "FirstQuote"),
                                                                  quoteType(name = "SecondQuote")))) becomes
            placeholder

        "Inner$nextLowerCasePlaceholder = int; Outer$lowerCasePlaceholder = Inner$lowerCasePlaceholder" describesThat
            namedType(name = "Outer$lowerCasePlaceholder",
                      type = namedType(name = "Inner$lowerCasePlaceholder", type = intType)) becomes
            "Outer$lowerCasePlaceholder"

        "inner$nextPlaceholder = nat; outer$placeholder = inner$placeholder" describesThat
            namedType(name = "outer$placeholder",
                      type = namedType(name = "inner$placeholder", type = natType)) becomes
            "Outer$placeholder"

        "FirstQualifier`$nextLowerCasePlaceholder = bool" describesThat
            namedType(qualifier = "FirstQualifier", name = placeholder, type = boolType) becomes
            "FirstQualifier.$placeholder"

        "secondQualifier`$nextLowerCasePlaceholder = char" describesThat
            namedType(qualifier = "secondQualifier", name = lowerCasePlaceholder, type = charType) becomes
            "SecondQualifier.$placeholder"
    }
}
