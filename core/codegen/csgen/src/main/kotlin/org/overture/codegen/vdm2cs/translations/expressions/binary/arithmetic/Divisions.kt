package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ADivideNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsDivideExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Divisions : BinaryExpressionTranslationRule<ADivideNumericBinaryExpIR>
{
    override fun translate(irNode: ADivideNumericBinaryExpIR)
        = CsDivideExpression(irNode.left.translate, irNode.right.translate)
}
