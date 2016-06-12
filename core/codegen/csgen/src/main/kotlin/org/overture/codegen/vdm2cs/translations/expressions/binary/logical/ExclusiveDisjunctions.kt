package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.AXorBoolBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsXOrExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object ExclusiveDisjunctions : BinaryExpressionTranslationRule<AXorBoolBinaryExpIR>
{
    override fun translate(irNode: AXorBoolBinaryExpIR)
        = CsXOrExpression(irNode.left.translate, irNode.right.translate)
}
