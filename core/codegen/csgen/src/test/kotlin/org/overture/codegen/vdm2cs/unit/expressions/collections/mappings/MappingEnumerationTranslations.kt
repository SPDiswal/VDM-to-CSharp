package org.overture.codegen.vdm2cs.unit.expressions.collections.mappings

import org.overture.codegen.ir.expressions.AEnumMapExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.mappings.MappingEnumerations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MappingEnumerationTranslations : ExpressionTranslationRuleSpek<AEnumMapExpIR>(MappingEnumerations)
{
    init
    {
        "{ |-> }: map bool to seq of char" describesThat
            emptyMappingEnumeration(from = boolType, to = seqOf(charType)) becomes
            "new Dictionary<bool, string>()"

        "{ |-> }: map int to int" describesThat
            emptyMappingEnumeration(from = intType, to = intType) becomes
            "new Dictionary<int, int>()"

        "{ 1 |-> 2, 3 |-> 4 }: map int to int" describesThat
            mappingEnumeration(1.toLiteral to 2.toLiteral,
                               3.toLiteral to 4.toLiteral) becomes
            "new Dictionary<int, int> { [1] = 2, [3] = 4 }"

        "{ 5 |-> \"Five\", 9 |-> \"Nine\" }: map int to seq of char" describesThat
            mappingEnumeration(5.toLiteral to "Five".toLiteral,
                               9.toLiteral to "Nine".toLiteral) becomes
            "new Dictionary<int, string> { [5] = \"Five\", [9] = \"Nine\" }"

        "{ 1 |-> { |-> }, 2 |-> { 3 |-> 4 } }: map int to map int to int" describesThat
            mappingEnumeration(1.toLiteral to emptyMappingEnumeration(from = intType, to = intType),
                               2.toLiteral to mappingEnumeration(3.toLiteral to 4.toLiteral)) becomes
            "new Dictionary<int, Dictionary<int, int>> { [1] = new Dictionary<int, int>(), " +
            "                                             [2] = new Dictionary<int, int> { [3] = 4 } }"
    }
}
