package org.overture.codegen.vdm2cs.unit.bindings

import org.overture.codegen.ir.patterns.ASetMultipleBindIR
import org.overture.codegen.vdm2cs.translations.bindings.MultipleSetBindings
import org.overture.codegen.vdm2cs.utilities.*

class MultipleSetBindingTranslations : MultipleBindingTranslationRuleSpek<ASetMultipleBindIR>(MultipleSetBindings)
{
    init
    {
        "x in set { }" describesThat
            setBinding(patterns = listOf(identifierPattern("x")),
                       set = emptySetEnumeration(intType)) becomes
            "from x in new HashSet<int>() " +
            "select new { x }"

        "a, b in set { 1, ..., 3 }" describesThat
            setBinding(patterns = listOf(identifierPattern("a"),
                                         identifierPattern("b")),
                       set = setRange(1.toLiteral, 3.toLiteral)) becomes
            "from a in Enumerable.Range(1, 3).ToHashSet() " +
            "from b in Enumerable.Range(1, 3).ToHashSet() " +
            "select new { a, b }"
    }
}
