package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ASubtractNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsMinusExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Subtractions : BinaryExpressionTranslationRule<ASubtractNumericBinaryExpIR>
{
    override fun translate(irNode: ASubtractNumericBinaryExpIR)
        = CsMinusExpression(irNode.left.translate, irNode.right.translate)
}
