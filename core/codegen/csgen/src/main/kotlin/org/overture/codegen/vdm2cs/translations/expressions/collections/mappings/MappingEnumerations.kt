package org.overture.codegen.vdm2cs.translations.expressions.collections.mappings

import org.overture.codegen.ir.expressions.AEnumMapExpIR
import org.overture.codegen.ir.types.SMapTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object MappingEnumerations : ExpressionTranslationRule<AEnumMapExpIR>
{
    override fun translate(irNode: AEnumMapExpIR) = let {
        val domainType = (irNode.type as SMapTypeIR).from.inline
        val rangeType = (irNode.type as SMapTypeIR).to.inline
        val entries = irNode.members.map { "[${it.left.inline}] = ${it.right.inline}" }

        when (irNode.members.size)
        {
            0    -> parseExpression("new Dictionary<$domainType, $rangeType>()")
            else -> parseExpression("new Dictionary<$domainType, $rangeType> { ${entries.joinToString(", ")} }")
        }
    }
}
