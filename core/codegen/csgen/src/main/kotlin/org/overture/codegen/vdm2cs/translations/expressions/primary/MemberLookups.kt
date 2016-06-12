package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.expressions.AFieldExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object MemberLookups : ExpressionTranslationRule<AFieldExpIR>
{
    override fun translate(irNode: AFieldExpIR)
        = parseExpression("(${irNode.`object`.inline}).${irNode.memberName.toUpperCamelCase()}")
}
