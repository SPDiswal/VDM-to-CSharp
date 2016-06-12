package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.AEqualsBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.Equalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class EqualityTranslations : ExpressionTranslationRuleSpek<AEqualsBinaryExpIR>(Equalities)
{
    init
    {
        "1 = 2" describesThat
            (1.toLiteral isEqualTo 2.toLiteral) becomes
            "1 == 2"

        "355 = 113" describesThat
            (355.toLiteral isEqualTo 113.toLiteral) becomes
            "355 == 113"

        "(1.2 = 3.4) = false" describesThat
            ((1.2.toLiteral isEqualTo 3.4.toLiteral) isEqualTo false.toLiteral) becomes
            "(1.2m == 3.4m) == false"

        "true = (3.4 = 5.6)" describesThat
            (true.toLiteral isEqualTo (3.4.toLiteral isEqualTo 5.6.toLiteral)) becomes
            "true == (3.4m == 5.6m)"

        "a: bool = b: bool" describesThat
            (identifier("a", boolType) isEqualTo identifier("b", boolType)) becomes
            "a == b"

        //region STRINGS

        "a: seq of char = b: seq of char" describesThat
            (identifier("a", seqOf(charType)) isEqualTo identifier("b", seqOf(charType))) becomes
            "a == b"

        "a: string = b: seq of char" describesThat
            (identifier("a", stringType) isEqualTo identifier("b", seqOf(charType))) becomes
            "a == b"

        "a: seq of char = b: string" describesThat
            (identifier("a", seqOf(charType)) isEqualTo identifier("b", stringType)) becomes
            "a == b"

        "a: string = b: string" describesThat
            (identifier("a", stringType) isEqualTo identifier("b", stringType)) becomes
            "a == b"

        //endregion

        //region SETS

        "a: set of int = b: set of int" describesThat
            (identifier("a", setOf(intType)) isEqualTo identifier("b", setOf(intType))) becomes
            "a.SetEquals(b)"

        "a = { }" describesThat
            (identifier("a", setOf(intType)) isEqualTo emptySetEnumeration(intType)) becomes
            "!a.Any()"

        "{ } = b" describesThat
            (emptySetEnumeration(intType) isEqualTo identifier("b", setOf(intType))) becomes
            "!b.Any()"

        //endregion

        //region SEQUENCES

        "a: seq of int = b: seq of int" describesThat
            (identifier("a", seqOf(intType)) isEqualTo identifier("b", seqOf(intType))) becomes
            "a.SequenceEqual(b)"

        "a = [ ]" describesThat
            (identifier("a", seqOf(intType)) isEqualTo emptySequenceEnumeration(intType)) becomes
            "!a.Any()"

        "[ ] = b" describesThat
            (emptySequenceEnumeration(intType) isEqualTo identifier("b", seqOf(intType))) becomes
            "!b.Any()"

        //endregion

        //region MAPPINGS

        "a: map int to int = b: map int to int" describesThat
            (identifier("a", map(intType to intType)) isEqualTo
                identifier("b", map(intType to intType))) becomes
            "a.DictionaryEquals(b)"

        "a = { |-> }" describesThat
            (identifier("a", map(intType to intType)) isEqualTo
                emptyMappingEnumeration(from = intType, to = intType)) becomes
            "!a.Any()"

        "{ |-> } = b" describesThat
            (identifier("b", map(intType to intType)) isEqualTo
                emptyMappingEnumeration(from = intType, to = intType)) becomes
            "!b.Any()"

        //endregion

        //region TUPLES

        "a: bool * int = b: bool * int" describesThat
            (identifier("a", tupleOf(boolType, intType)) isEqualTo identifier("b", tupleOf(boolType, intType))) becomes
            "object.Equals(a, b)"

        //endregion

        //region RECORDS

        "a: Alpha = b: Alpha" describesThat
            (identifier("a", recordType(name = "Alpha")) isEqualTo identifier("b", recordType(name = "Alpha"))) becomes
            "object.Equals(a, b)"

        //endregion
    }
}
