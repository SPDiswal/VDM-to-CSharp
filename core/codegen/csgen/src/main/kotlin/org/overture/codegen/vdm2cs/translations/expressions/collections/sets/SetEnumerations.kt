package org.overture.codegen.vdm2cs.translations.expressions.collections.sets

import org.overture.codegen.ir.expressions.AEnumSetExpIR
import org.overture.codegen.ir.types.SSetTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SetEnumerations : ExpressionTranslationRule<AEnumSetExpIR>
{
    override fun translate(irNode: AEnumSetExpIR) = let {
        val elementType = (irNode.type as SSetTypeIR).setOf.inline

        when (irNode.members.size)
        {
            0    -> parseExpression("new HashSet<$elementType>()")
            else -> parseExpression("new HashSet<$elementType> { ${irNode.members.joinExpressions} }")
        }
    }
}
