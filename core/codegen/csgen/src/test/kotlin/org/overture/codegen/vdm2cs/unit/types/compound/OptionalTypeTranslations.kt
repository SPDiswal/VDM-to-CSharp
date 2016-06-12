package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.vdm2cs.translations.types.Types
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class OptionalTypeTranslations : TypeTranslationRuleSpek<STypeIR>(Types)
{
    init
    {
        "[bool]" describesThat
            optional(boolType) becomes
            "bool?"

        "[char]" describesThat
            optional(charType) becomes
            "char?"

        "[int]" describesThat
            optional(intType) becomes
            "int?"

        "[nat]" describesThat
            optional(natType) becomes
            "int?"

        "[nat1]" describesThat
            optional(nat1Type) becomes
            "int?"

        "[rat]" describesThat
            optional(ratType) becomes
            "decimal?"

        "[real]" describesThat
            optional(realType) becomes
            "decimal?"

        "[token]" describesThat
            optional(tokenType) becomes
            "Token"

        "[map bool to bool]" describesThat
            optional(map(boolType to boolType)) becomes
            "Dictionary<bool, bool>"

        "[map [bool] to [bool]]" describesThat
            optional(map(optional(boolType) to optional(boolType))) becomes
            "Dictionary<bool?, bool?>"

        "[int * int]" describesThat
            optional(tupleOf(intType, intType)) becomes
            "Tuple<int, int>"

        "[[int] * [int]]" describesThat
            optional(tupleOf(optional(intType), optional(intType))) becomes
            "Tuple<int?, int?>"

        "[seq of char]" describesThat
            optional(seqOf(charType)) becomes
            "string"

        "[seq of int]" describesThat
            optional(seqOf(intType)) becomes
            "List<int>"

        "[seq of [int]]" describesThat
            optional(seqOf(optional(intType))) becomes
            "List<int?>"

        "[set of int]" describesThat
            optional(setOf(intType)) becomes
            "HashSet<int>"

        "[set of [int]]" describesThat
            optional(setOf(optional(intType))) becomes
            "HashSet<int?>"

        "[int | char]" describesThat
            optional(unionOf(intType, charType)) becomes
            "object"

        "[int -> int]" describesThat
            optional(methodType(listOf(intType), intType)) becomes
            "Func<int, int>"

        "[$nextPlaceholder = bool]" describesThat
            optional(namedType(name = placeholder, type = boolType)) becomes
            placeholder

        "[$nextPlaceholder = [bool]]" describesThat
            optional(namedType(name = placeholder, type = optional(boolType))) becomes
            placeholder

        "[$nextPlaceholder]" describesThat
            optional(recordType(name = placeholder)) becomes
            placeholder

        "[<$nextLowerCasePlaceholder>]" describesThat
            optional(quoteType(lowerCasePlaceholder)) becomes
            "Quote?"

        "[<firstQuote> | <SecondQuote>]" describesThat
            optional(unionOf(quoteType(name = "firstQuote"), quoteType(name = "SecondQuote"))) becomes
            "Quote?"

        "[$nextLowerCasePlaceholder = <firstQuote> | <SecondQuote>]" describesThat
            optional(namedType(name = lowerCasePlaceholder, type = unionOf(quoteType(name = "firstQuote"),
                                                                           quoteType(name = "SecondQuote")))) becomes
            "$placeholder"

        "[$nextPlaceholder = [<FirstQuote> | <SecondQuote>]]" describesThat
            optional(namedType(name = placeholder, type = optional(unionOf(quoteType(name = "FirstQuote"),
                                                                           quoteType(name = "SecondQuote"))))) becomes
            "$placeholder"
    }
}
