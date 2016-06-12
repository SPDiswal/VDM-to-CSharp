package org.overture.codegen.vdm2cs.translations.expressions.unary.mappings

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Domains : UnaryExpressionTranslationRule<AMapDomainUnaryExpIR>
{
    override fun translate(irNode: AMapDomainUnaryExpIR)
        = parseExpression("(${irNode.exp.inline}).Keys.ToHashSet()")
}
