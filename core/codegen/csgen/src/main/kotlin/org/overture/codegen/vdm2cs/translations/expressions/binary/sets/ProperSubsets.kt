package org.overture.codegen.vdm2cs.translations.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetProperSubsetBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object ProperSubsets : BinaryExpressionTranslationRule<ASetProperSubsetBinaryExpIR>
{
    override fun translate(irNode: ASetProperSubsetBinaryExpIR)
        = parseExpression("(${irNode.left.inline}).IsProperSubsetOf(${irNode.right.inline})")
}
