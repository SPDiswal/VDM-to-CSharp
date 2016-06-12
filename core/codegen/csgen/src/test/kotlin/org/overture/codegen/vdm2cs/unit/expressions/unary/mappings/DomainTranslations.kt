package org.overture.codegen.vdm2cs.unit.expressions.unary.mappings

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.mappings.Domains
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.Cardinalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DomainTranslations : ExpressionTranslationRuleSpek<AMapDomainUnaryExpIR>(Domains)
{
    init
    {
        "dom { |-> }" describesThat
            domain(emptyMappingEnumeration(from = intType, to = intType)) becomes
            "new Dictionary<int, int>().Keys.ToHashSet()"

        "dom { 1 |-> 2 }" describesThat
            domain(mappingEnumeration(1.toLiteral to 2.toLiteral)) becomes
            "new Dictionary<int, int> { [1] = 2 }.Keys.ToHashSet()"
    }
}
