package org.overture.codegen.vdm2cs.unit.bindings

import org.overture.codegen.ir.patterns.ASetBindIR
import org.overture.codegen.vdm2cs.translations.bindings.SingleSetBindings
import org.overture.codegen.vdm2cs.utilities.*

class SingleSetBindingTranslations : SingleBindingTranslationRuleSpek<ASetBindIR>(SingleSetBindings)
{
    init
    {
        "x in set { }" describesThat
            setBinding(pattern = identifierPattern("x"), set = emptySetEnumeration(intType)) becomes
            "from x in new HashSet<int>() " +
            "select new { x }"

        "a in set { 1, ..., 3 }" describesThat
            setBinding(pattern = identifierPattern("a"), set = setRange(1.toLiteral, 3.toLiteral)) becomes
            "from a in Enumerable.Range(1, 3).ToHashSet() " +
            "select new { a }"
    }
}
