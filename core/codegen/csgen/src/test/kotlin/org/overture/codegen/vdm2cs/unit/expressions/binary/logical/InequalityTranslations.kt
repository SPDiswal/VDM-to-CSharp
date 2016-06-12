package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.ANotEqualsBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.Inequalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class InequalityTranslations : ExpressionTranslationRuleSpek<ANotEqualsBinaryExpIR>(Inequalities)
{
    init
    {
        "1 <> 2" describesThat
            (1.toLiteral isNotEqualTo 2.toLiteral) becomes
            "1 != 2"

        "355 <> 113" describesThat
            (355.toLiteral isNotEqualTo 113.toLiteral) becomes
            "355 != 113"

        "(1.2 <> 3.4) <> false" describesThat
            ((1.2.toLiteral isNotEqualTo 3.4.toLiteral) isNotEqualTo false.toLiteral) becomes
            "(1.2m != 3.4m) != false"

        "true <> (3.4 <> 5.6)" describesThat
            (true.toLiteral isNotEqualTo (3.4.toLiteral isNotEqualTo 5.6.toLiteral)) becomes
            "true != (3.4m != 5.6m)"

        "(1 > 2) <> true" describesThat
            ((1.toLiteral isGreaterThan 2.toLiteral) isNotEqualTo true.toLiteral) becomes
            "(1 > 2) != true"

        "(1 >= 2) <> true" describesThat
            ((1.toLiteral isGreaterThanOrEqualTo 2.toLiteral) isNotEqualTo true.toLiteral) becomes
            "(1 >= 2) != true"

        "(1 < 2) <> true" describesThat
            ((1.toLiteral isLessThan 2.toLiteral) isNotEqualTo true.toLiteral) becomes
            "(1 < 2) != true"

        "(1 <= 2) <> true" describesThat
            ((1.toLiteral isLessThanOrEqualTo 2.toLiteral) isNotEqualTo true.toLiteral) becomes
            "(1 <= 2) != true"

        "a: bool <> b: bool" describesThat
            (identifier("a", boolType) isNotEqualTo identifier("b", boolType)) becomes
            "a != b"

        //region STRINGS

        "a: seq of char <> b: seq of char" describesThat
            (identifier("a", seqOf(charType)) isNotEqualTo identifier("b", seqOf(charType))) becomes
            "a != b"

        "a: seq of char <> b: string" describesThat
            (identifier("a", seqOf(charType)) isNotEqualTo identifier("b", stringType)) becomes
            "a != b"

        "a: string <> b: seq of char" describesThat
            (identifier("a", stringType) isNotEqualTo identifier("b", seqOf(charType))) becomes
            "a != b"

        "a: string <> b: string" describesThat
            (identifier("a", stringType) isNotEqualTo identifier("b", stringType)) becomes
            "a != b"

        //endregion

        //region SETS

        "a: set of int <> b: set of int" describesThat
            (identifier("a", setOf(intType)) isNotEqualTo identifier("b", setOf(intType))) becomes
            "!a.SetEquals(b)"

        "a: set of int <> { }" describesThat
            (identifier("a", setOf(intType)) isNotEqualTo emptySetEnumeration(intType)) becomes
            "a.Any()"

        "{ } <> b: set of int" describesThat
            (emptySetEnumeration(intType) isNotEqualTo identifier("b", setOf(intType))) becomes
            "b.Any()"

        //endregion

        //region SEQUENCES

        "a: seq of int <> b: seq of int" describesThat
            (identifier("a", seqOf(intType)) isNotEqualTo identifier("b", seqOf(intType))) becomes
            "!a.SequenceEqual(b)"

        "a: seq of int <> [ ]" describesThat
            (identifier("a", seqOf(intType)) isNotEqualTo emptySequenceEnumeration(intType)) becomes
            "a.Any()"

        "[ ] <> b: seq of int" describesThat
            (emptySequenceEnumeration(intType) isNotEqualTo identifier("b", seqOf(intType))) becomes
            "b.Any()"

        //endregion

        //region MAPPINGS

        "a: map int to int <> b: map int to int" describesThat
            (identifier("a", map(intType to intType)) isNotEqualTo
                identifier("b", map(intType to intType))) becomes
            "!a.DictionaryEquals(b)"

        "a: map int to int <> { |-> }" describesThat
            (identifier("a", map(intType to intType)) isNotEqualTo
                emptyMappingEnumeration(from = intType, to = intType)) becomes
            "a.Any()"

        "{ |-> } <> b: map int to int" describesThat
            (emptyMappingEnumeration(from = intType, to = intType) isNotEqualTo
                identifier("b", map(intType to intType))) becomes
            "b.Any()"

        //endregion

        //region TUPLES

        "a: bool * int <> b: bool * int" describesThat
            (identifier("a", tupleOf(boolType, intType)) isNotEqualTo
                identifier("b", tupleOf(boolType, intType))) becomes
            "!object.Equals(a, b)"

        //endregion

        //region RECORDS

        "a: Alpha <> b: Alpha" describesThat
            (identifier("a", recordType(name = "Alpha")) isNotEqualTo
                identifier("b", recordType(name = "Alpha"))) becomes
            "!object.Equals(a, b)"

        //endregion
    }
}
