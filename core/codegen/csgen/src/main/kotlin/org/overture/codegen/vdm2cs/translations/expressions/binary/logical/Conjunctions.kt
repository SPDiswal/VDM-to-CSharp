package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.AAndBoolBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsAndExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Conjunctions : BinaryExpressionTranslationRule<AAndBoolBinaryExpIR>
{
    override fun translate(irNode: AAndBoolBinaryExpIR)
        = CsAndExpression(irNode.left.translate, irNode.right.translate)
}
