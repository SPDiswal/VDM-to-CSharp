package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.AQuoteLiteralExpIR
import org.overture.codegen.vdm2cs.utilities.parseExpression
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

object QuoteLiterals : LiteralTranslationRule<AQuoteLiteralExpIR>
{
    override fun translate(irNode: AQuoteLiteralExpIR)
        = parseExpression("Quote.${irNode.value.toUpperCamelCase()}")
}
