package org.overture.codegen.vdm2cs.unit.types

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.givenData
import org.overture.codegen.ir.STypeIR
import org.overture.codegen.vdm2cs.framework.TranscompilerSpekBase
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

class TypeInvariantTranslations : TranscompilerSpekBase()
{
    private val data: List<Expectation> = listOf(

        //region BOUNDS (nat and nat1)

        natType becomes { "$it >= 0" },
        optional(natType) becomes { "($it == null || $it >= 0)" },

        nat1Type becomes { "$it > 0" },
        optional(nat1Type) becomes { "($it == null || $it > 0)" },

        //endregion

        //region NON-NULL (type aliases, records, tokens, strings)

        namedType(name = "Alias", type = nat1Type) becomes { "$it != null" },
        optional(namedType(name = "Alias", type = nat1Type)) becomes { null },

        recordType(name = "Record") becomes { "$it != null" },
        optional(recordType(name = "Record")) becomes { null },

        tokenType becomes { "$it != null" },
        optional(tokenType) becomes { null },

        stringType becomes { "$it != null" },
        optional(stringType) becomes { null },

        seqOf(charType) becomes { "$it != null" },
        optional(seqOf(charType)) becomes { null },

        seq1Of(charType) becomes { "$it != null && $it.Any()" },
        optional(seq1Of(charType)) becomes { "($it == null || $it.Any())" },

        //endregion

        //region SET CONSISTENCY

        setOf(boolType) becomes { "$it != null" },
        setOf(nat1Type) becomes { "$it != null && ${forAll(it, "_", "_ > 0")}" },
        setOf(optional(nat1Type)) becomes { "$it != null && ${forAll(it, "_", "(_ == null || _ > 0)")}" },
        setOf(optional(namedType(name = "Alias", type = nat1Type))) becomes { "$it != null" },
        optional(setOf(boolType)) becomes { null },
        optional(setOf(nat1Type)) becomes { "($it == null || ${forAll(it, "_", "_ > 0")})" },
        setOf(setOf(nat1Type)) becomes {
            "$it != null && " +
            "${forAll(it, "_", "_ != null && " +
                               "${forAll("_", "__", "__ > 0")}")}"
        },

        //endregion

        //region SEQUENCE CONSISTENCY

        seqOf(boolType) becomes { "$it != null" },
        seqOf(nat1Type) becomes { "$it != null && ${forAll(it, "_", "_ > 0")}" },
        seqOf(optional(nat1Type)) becomes { "$it != null && ${forAll(it, "_", "(_ == null || _ > 0)")}" },
        seqOf(optional(namedType(name = "Alias", type = nat1Type))) becomes { "$it != null" },
        optional(seqOf(boolType)) becomes { null },
        optional(seqOf(nat1Type)) becomes { "($it == null || ${forAll(it, "_", "_ > 0")})" },
        seqOf(seqOf(nat1Type)) becomes {
            "$it != null && " +
            "${forAll(it, "_", "_ != null && " +
                               "${forAll("_", "__", "__ > 0")}")}"
        },

        seq1Of(boolType) becomes { "$it != null && $it.Any()" },
        seq1Of(nat1Type) becomes { "$it != null && $it.Any() && ${forAll(it, "_", "_ > 0")}" },
        optional(seq1Of(boolType)) becomes { "($it == null || $it.Any())" },
        optional(seq1Of(nat1Type)) becomes { "($it == null || $it.Any() && ${forAll(it, "_", "_ > 0")})" },
        seq1Of(seq1Of(nat1Type)) becomes {
            "$it != null && $it.Any() && " +
            "${forAll(it, "_", "_ != null && _.Any() && " +
                               "${forAll("_", "__", "__ > 0")}")}"
        },

        //endregion

        //region MAPPING CONSISTENCY

        map(boolType to boolType) becomes { "$it != null" },
        map(nat1Type to boolType) becomes { "$it != null && ${forAll("$it.Keys", "_", "_ > 0")}" },
        map(boolType to nat1Type) becomes { "$it != null && ${forAll("$it.Values", "_", "_ > 0")}" },
        map(nat1Type to nat1Type) becomes {
            "$it != null && " +
            "${forAll("$it.Keys", "_", "_ > 0")} && " +
            "${forAll("$it.Values", "_", "_ > 0")}"
        },
        optional(map(boolType to boolType)) becomes { null },
        optional(map(nat1Type to boolType)) becomes {
            "($it == null || ${forAll("$it.Keys", "_", "_ > 0")})"
        },
        map(nat1Type to map(natType to nat1Type)) becomes {
            "$it != null && " +
            "${forAll("$it.Keys", "_", "_ > 0")} && " +
            "${forAll("$it.Values", "_", "_ != null && " +
                                         "${forAll("_.Keys", "__", "__ >= 0")} && " +
                                         "${forAll("_.Values", "__", "__ > 0")}")}"
        },

        inmap(boolType to boolType) becomes { "$it != null && $it.IsInjective()" },
        inmap(nat1Type to boolType) becomes {
            "$it != null && $it.IsInjective() && ${forAll("$it.Keys", "_", "_ > 0")}"
        },
        inmap(boolType to nat1Type) becomes {
            "$it != null && $it.IsInjective() && ${forAll("$it.Values", "_", "_ > 0")}"
        },
        inmap(nat1Type to nat1Type) becomes {
            "$it != null && $it.IsInjective() && " +
            "${forAll("$it.Keys", "_", "_ > 0")} && " +
            "${forAll("$it.Values", "_", "_ > 0")}"
        },
        optional(inmap(boolType to boolType)) becomes { "($it == null || $it.IsInjective())" },
        optional(inmap(nat1Type to boolType)) becomes {
            "($it == null || $it.IsInjective() && ${forAll("$it.Keys", "_", "_ > 0")})"
        },
        inmap(nat1Type to inmap(natType to nat1Type)) becomes {
            "$it != null && $it.IsInjective() && " +
            "${forAll("$it.Keys", "_", "_ > 0")} && " +
            "${forAll("$it.Values", "_", "_ != null && _.IsInjective() && " +
                                         "${forAll("_.Keys", "__", "__ >= 0")} && " +
                                         "${forAll("_.Values", "__", "__ > 0")}")}"
        },

        //endregion

        //region TUPLES

        tupleOf(boolType, boolType) becomes { "$it != null" },
        tupleOf(natType, nat1Type) becomes { "$it != null && $it.Item1 >= 0 && $it.Item2 > 0" },
        tupleOf(boolType, seqOf(charType), intType, natType, ratType) becomes {
            "$it != null && $it.Item2 != null && $it.Item4 >= 0"
        },
        tupleOf(tupleOf(boolType, nat1Type), tupleOf(natType, ratType)) becomes {
            "$it != null && $it.Item1 != null && $it.Item1.Item2 > 0 && $it.Item2 != null && $it.Item2.Item1 >= 0"
        },
        optional(tupleOf(boolType, boolType)) becomes { null },
        optional(tupleOf(natType, nat1Type)) becomes { "($it == null || $it.Item1 >= 0 && $it.Item2 > 0)" },
        optional(tupleOf(boolType, seqOf(charType), intType, natType, ratType)) becomes {
            "($it == null || $it.Item2 != null && $it.Item4 >= 0)"
        },
        optional(tupleOf(optional(tupleOf(boolType, nat1Type)), optional(tupleOf(natType, ratType)))) becomes {
            "($it == null || ($it.Item1 == null || $it.Item1.Item2 > 0) && ($it.Item2 == null || $it.Item2.Item1 >= 0))"
        },

        //endregion

        //region UNIONS

        unionOf(boolType, intType) becomes { "$it is bool || $it is int" },
        unionOf(boolType, natType, tokenType, seqOf(charType)) becomes {
            "$it is bool || $it is int && $it >= 0 || $it is Token || $it is string"
        },
        unionOf(unionOf(boolType, natType), unionOf(tokenType, seqOf(charType))) becomes {
            "$it is bool || $it is int && $it >= 0 || $it is Token || $it is string"
        },
        optional(unionOf(boolType, intType)) becomes { "($it == null || $it is bool || $it is int)" },
        unionOf(optional(nat1Type), seqOf(charType)) becomes {
            "$it is int? && ($it == null || $it > 0) || $it is string"
        },
        optional(unionOf(optional(nat1Type), seqOf(charType))) becomes {
            "($it == null || $it is int? && ($it == null || $it > 0) || $it is string)"
        },
        unionOf(seqOf(charType), unionOf(quoteType("First"), quoteType("Second"), intType, boolType)) becomes {
            "$it is string || $it is int || $it is bool || $it is Quote && ($it == Quote.First || $it == Quote.Second)"
        },

        //endregion

        //region QUOTES

        quoteType("First") becomes { "$it == Quote.First" },
        quoteType("Second") becomes { "$it == Quote.Second" },
        unionOf(quoteType("First"), quoteType("Second")) becomes { "($it == Quote.First || $it == Quote.Second)" },
        unionOf(quoteType("First"), quoteType("Second"), quoteType("Third")) becomes {
            "($it == Quote.First || $it == Quote.Second || $it == Quote.Third)"
        },
        optional(unionOf(quoteType("First"), quoteType("Second"))) becomes {
            "($it == null || $it == Quote.First || $it == Quote.Second)"
        },
        optional(unionOf(quoteType("First"), quoteType("Second"), quoteType("Third"))) becomes {
            "($it == null || $it == Quote.First || $it == Quote.Second || $it == Quote.Third)"
        }

        //endregion
    )
    
    init
    {
        givenData(data)
        {
            val (type, expression, expectedInvariants) = it
            
            on("translation")
            {
                val actualInvariants = TypeInvariants.generateInvariant(type, expression)

                it("has the expected invariants") { assertThat(actualInvariants, equalTo(expectedInvariants)) }
            }
        }
    }
    
    private infix fun STypeIR.becomes(expectedInvariant: (String) -> String?)
        = Expectation(this, nextLowerCasePlaceholder, expectedInvariant(lowerCasePlaceholder))
    
    private data class Expectation(val type: STypeIR, val expression: String, val expectedInvariant: String?)
    {
        override fun toString() = "$expression: ${type.javaClass.simpleName} -> $expectedInvariant"
    }
}
