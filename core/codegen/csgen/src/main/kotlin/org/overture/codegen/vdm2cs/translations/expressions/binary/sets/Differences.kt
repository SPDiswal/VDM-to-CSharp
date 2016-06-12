package org.overture.codegen.vdm2cs.translations.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetDifferenceBinaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Differences : BinaryExpressionTranslationRule<ASetDifferenceBinaryExpIR>
{
    override fun translate(irNode: ASetDifferenceBinaryExpIR)
        = parseExpression("(${irNode.left.inline}).Except(${irNode.right.inline}).ToHashSet()")
}
