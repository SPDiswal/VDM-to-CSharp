package org.overture.codegen.vdm2cs.translations.expressions.binary.mappings

import org.overture.codegen.ir.expressions.AMapOverrideBinaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Overrides : BinaryExpressionTranslationRule<AMapOverrideBinaryExpIR>
{
    override fun translate(irNode: AMapOverrideBinaryExpIR)
        = parseExpression("${irNode.left.inline}.OverrideBy(${irNode.right.inline}).ToDictionary()")
}
