package org.overture.codegen.vdm2cs.unit.expressions.collections.sets

import org.overture.codegen.ir.expressions.AEnumSetExpIR
import org.overture.codegen.vdm2cs.translations.expressions.collections.sets.SetEnumerations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SetEnumerationTranslations : ExpressionTranslationRuleSpek<AEnumSetExpIR>(SetEnumerations)
{
    init
    {
        "{ }: set of int" describesThat
            emptySetEnumeration(intType) becomes
            "new HashSet<int>()"

        "{ }: set of seq of char" describesThat
            emptySetEnumeration(seqOf(charType)) becomes
            "new HashSet<string>()"

        "{ 1, 2, 3 }: set of int" describesThat
            setEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral) becomes
            "new HashSet<int> { 1, 2, 3 }"

        "{ \"Hello\", \"World\" }: set of seq of char" describesThat
            setEnumeration("Hello".toLiteral, "World".toLiteral) becomes
            "new HashSet<string> { \"Hello\", \"World\" }"

        "{ { }, { 1, 2 } }: set of set of int" describesThat
            setEnumeration(emptySetEnumeration(intType),
                           setEnumeration(1.toLiteral, 2.toLiteral)) becomes
            "new HashSet<HashSet<int>> { new HashSet<int>(), new HashSet<int> { 1, 2 } }"
    }
}
