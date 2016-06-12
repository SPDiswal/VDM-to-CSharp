package org.overture.codegen.vdm2cs.unit.expressions.unary.mappings

import org.overture.codegen.ir.expressions.AMapRangeUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.mappings.Ranges
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class RangeTranslations : ExpressionTranslationRuleSpek<AMapRangeUnaryExpIR>(Ranges)
{
    init
    {
        "rng { |-> }" describesThat
            range(emptyMappingEnumeration(from = intType, to = intType)) becomes
            "new Dictionary<int, int>().Values.ToHashSet()"

        "rng { 1 |-> 2 }" describesThat
            range(mappingEnumeration(1.toLiteral to 2.toLiteral)) becomes
            "new Dictionary<int, int> { [1] = 2 }.Values.ToHashSet()"
    }
}
