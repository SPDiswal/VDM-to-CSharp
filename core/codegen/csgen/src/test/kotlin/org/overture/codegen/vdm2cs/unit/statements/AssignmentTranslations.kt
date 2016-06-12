package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.AAssignmentStmIR
import org.overture.codegen.vdm2cs.translations.statements.Assignments
import org.overture.codegen.vdm2cs.utilities.*

class AssignmentTranslations : StatementTranslationRuleSpek<AAssignmentStmIR>(Assignments)
{
    // TODO Pass-by-value assignments with variables on the right-hand-side (invoking the Copy method if necessary).

    val recordType = recordType(name = "Record")

    init
    {
        //region LOCAL VARIABLES

        "$nextPlaceholder := 1" describesThat
            assignment(stateDesignator = nameDesignator(placeholder, intType),
                       expression = 1.toLiteral) becomes
            "$lowerCasePlaceholder = 1;"

        "$nextLowerCasePlaceholder := 2" describesThat
            assignment(stateDesignator = nameDesignator(lowerCasePlaceholder, intType),
                       expression = 2.toLiteral) becomes
            "$lowerCasePlaceholder = 2;"

        "$nextPlaceholder := 1 + 2" describesThat
            assignment(stateDesignator = nameDesignator(placeholder, intType),
                       expression = 1.toLiteral plus 2.toLiteral) becomes
            "$lowerCasePlaceholder = 1 + 2;"

        //endregion

        //region STATE FIELDS

        "$nextPlaceholder := 1" describesThat
            assignment(stateDesignator = stateFieldNameDesignator(placeholder, intType),
                       expression = 1.toLiteral) becomes
            "State.$placeholder = 1;"

        "$nextLowerCasePlaceholder := 2" describesThat
            assignment(stateDesignator = stateFieldNameDesignator(lowerCasePlaceholder, intType),
                       expression = 2.toLiteral) becomes
            "State.$placeholder = 2;"

        "$nextPlaceholder := 1 + 2" describesThat
            assignment(stateDesignator = stateFieldNameDesignator(placeholder, intType),
                       expression = 1.toLiteral plus 2.toLiteral) becomes
            "State.$placeholder = 1 + 2;"

        //endregion

        //region FIELDS

        "$nextPlaceholder.first := 37" describesThat
            assignment(stateDesignator = fieldDesignator(nameDesignator(placeholder, recordType),
                                                         "first", intType),
                       expression = 37.toLiteral) becomes
            "$lowerCasePlaceholder.First = 37;"

        "$nextLowerCasePlaceholder.second := 42" describesThat
            assignment(stateDesignator = fieldDesignator(nameDesignator(lowerCasePlaceholder, recordType),
                                                         "second", intType),
                       expression = 42.toLiteral) becomes
            "$lowerCasePlaceholder.Second = 42;"

        "$nextPlaceholder(2 + 3).Value := 4" describesThat
            assignment(stateDesignator = fieldDesignator(
                indexerDesignator(
                    stateFieldNameDesignator(placeholder, seqOf(recordType)),
                    2.toLiteral plus 3.toLiteral, recordType),
                "Value", intType),
                       expression = 4.toLiteral) becomes
            "State.$placeholder[2 + 3].Value = 4;"

        //endregion

        //region INDEXERS

        "$nextPlaceholder(1) := 0" describesThat
            assignment(stateDesignator = indexerDesignator(
                nameDesignator(lowerCasePlaceholder, seqOf(intType)), 1.toLiteral, intType
            ),
                       expression = 0.toLiteral) becomes
            "$lowerCasePlaceholder[1] = 0;"

        "$nextLowerCasePlaceholder.Field(2) := 23" describesThat
            assignment(
                stateDesignator = indexerDesignator(
                    fieldDesignator(nameDesignator(lowerCasePlaceholder, recordType), "Field", seqOf(intType)),
                    2.toLiteral, intType),
                expression = 23.toLiteral
            ) becomes
            "$lowerCasePlaceholder.Field[2] = 23;"

        //endregion

        //region ARITHMETIC MANIPULATIONS

        //endregion

        //region SET MANIPULATIONS

        "a := a union { 1 }" describesThat
            assignment(stateDesignator = nameDesignator("a", setOf(intType)),
                       expression = identifier("a", setOf(intType)) union setEnumeration(1.toLiteral)) becomes
            "a.Add(1);"

        "b := { 2 } union b" describesThat
            assignment(stateDesignator = nameDesignator("b", setOf(intType)),
                       expression = setEnumeration(2.toLiteral) union identifier("b", setOf(intType))) becomes
            "b.Add(2);"

        "a := a union { 1, 2 }" describesThat
            assignment(stateDesignator = nameDesignator("a", setOf(intType)),
                       expression = identifier("a", setOf(intType)) union
                           setEnumeration(1.toLiteral, 2.toLiteral)) becomes
            "a.UnionWith(new HashSet<int> { 1, 2 });"

        "b := { 1, 2 } union b" describesThat
            assignment(stateDesignator = nameDesignator("b", setOf(intType)),
                       expression = setEnumeration(1.toLiteral, 2.toLiteral) union
                           identifier("b", setOf(intType))) becomes
            "b.UnionWith(new HashSet<int> { 1, 2 });"

        "a := a inter { 1 }" describesThat
            assignment(stateDesignator = nameDesignator("a", setOf(intType)),
                       expression = identifier("a", setOf(intType)) intersect setEnumeration(1.toLiteral)) becomes
            "a.IntersectWith(new HashSet<int> { 1 });"

        "b := { 2 } inter b" describesThat
            assignment(stateDesignator = nameDesignator("b", setOf(intType)),
                       expression = setEnumeration(2.toLiteral) intersect identifier("b", setOf(intType))) becomes
            "b.IntersectWith(new HashSet<int> { 2 });"

        "a := a inter { 1, 2 }" describesThat
            assignment(stateDesignator = nameDesignator("a", setOf(intType)),
                       expression = identifier("a", setOf(intType)) intersect
                           setEnumeration(1.toLiteral, 2.toLiteral)) becomes
            "a.IntersectWith(new HashSet<int> { 1, 2 });"

        "b := { 1, 2 } inter b" describesThat
            assignment(stateDesignator = nameDesignator("b", setOf(intType)),
                       expression = setEnumeration(1.toLiteral, 2.toLiteral) intersect
                           identifier("b", setOf(intType))) becomes
            "b.IntersectWith(new HashSet<int> { 1, 2 });"

        "a := a \\ { 1 }" describesThat
            assignment(stateDesignator = nameDesignator("a", setOf(intType)),
                       expression = identifier("a", setOf(intType)) except setEnumeration(1.toLiteral)) becomes
            "a.Remove(1);"

        "a := a \\ { 1, 2 }" describesThat
            assignment(stateDesignator = nameDesignator("a", setOf(intType)),
                       expression = identifier("a", setOf(intType)) except
                           setEnumeration(1.toLiteral, 2.toLiteral)) becomes
            "a.ExceptWith(new HashSet<int> { 1, 2 });"

        //endregion

        //region SEQUENCE MANIPULATIONS

        "a := a ^ [ 1 ]" describesThat
            assignment(stateDesignator = nameDesignator("a", seqOf(intType)),
                       expression = identifier("a", seqOf(intType)) concatenate
                           sequenceEnumeration(1.toLiteral)) becomes
            "a.Add(1);"

        "b := [ 2 ] ^ b" describesThat
            assignment(stateDesignator = nameDesignator("b", seqOf(intType)),
                       expression = sequenceEnumeration(2.toLiteral) concatenate
                           identifier("b", seqOf(intType))) becomes
            "b.Insert(2, 0);"

        "a := a ^ [ 1, 2 ]" describesThat
            assignment(stateDesignator = nameDesignator("a", seqOf(intType)),
                       expression = identifier("a", seqOf(intType)) concatenate
                           sequenceEnumeration(1.toLiteral, 2.toLiteral)) becomes
            "a.AddRange(new List<int> { 1, 2 });"

        "b := [ 1, 2 ] ^ b" describesThat
            assignment(stateDesignator = nameDesignator("b", seqOf(intType)),
                       expression = sequenceEnumeration(1.toLiteral, 2.toLiteral) concatenate
                           identifier("b", seqOf(intType))) becomes
            "b.InsertRange(new List<int> { 1, 2 }, 0);"

        //endregion

        //region MAPPING MANIPULATIONS

        "a := a munion { 1 |-> 2 }" describesThat
            assignment(stateDesignator = nameDesignator("a", map(intType to intType)),
                       expression = identifier("a", map(intType to intType)) merge
                           mappingEnumeration(1.toLiteral to 2.toLiteral)) becomes
            "a.Add(1, 2);"

        "b := { 3 |-> 4 } munion b" describesThat
            assignment(stateDesignator = nameDesignator("b", map(intType to intType)),
                       expression = mappingEnumeration(3.toLiteral to 4.toLiteral) merge
                           identifier("b", map(intType to intType))) becomes
            "b.Add(3, 4);"

        "a := a ++ { 1 |-> 2 }" describesThat
            assignment(stateDesignator = nameDesignator("a", map(intType to intType)),
                       expression = identifier("a", map(intType to intType)) override
                           mappingEnumeration(1.toLiteral to 2.toLiteral)) becomes
            "a[1] = 2;"

        "b := b ++ { 3 |-> 4 }" describesThat
            assignment(stateDesignator = nameDesignator("b", map(intType to intType)),
                       expression = identifier("b", map(intType to intType)) override
                           mappingEnumeration(3.toLiteral to 4.toLiteral)) becomes
            "b[3] = 4;"

        //endregion

        //region Pass-by-value for SETS

        //endregion

        //region Pass-by-value for SEQUENCES

        //endregion

        //region Pass-by-value for MAPPINGS

        //endregion

        //region Pass-by-value for TUPLES

        //endregion

        //region Pass-by-value for RECORDS

        //endregion
    }
}
