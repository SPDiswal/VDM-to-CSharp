package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.expressions.AFieldNumberExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object ItemLookups : ExpressionTranslationRule<AFieldNumberExpIR>
{
    override fun translate(irNode: AFieldNumberExpIR)
        = parseExpression("(${irNode.tuple.inline}).Item${irNode.field}")
}
