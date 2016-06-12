package org.overture.codegen.vdm2cs.translations.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetSubsetBinaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Subsets : BinaryExpressionTranslationRule<ASetSubsetBinaryExpIR>
{
    override fun translate(irNode: ASetSubsetBinaryExpIR)
        = parseExpression("(${irNode.left.inline}).IsSubsetOf(${irNode.right.inline})")
}
