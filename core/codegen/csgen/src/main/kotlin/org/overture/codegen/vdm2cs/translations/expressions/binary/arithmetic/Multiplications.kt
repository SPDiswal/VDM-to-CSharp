package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ATimesNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsTimesExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Multiplications : BinaryExpressionTranslationRule<ATimesNumericBinaryExpIR>
{
    override fun translate(irNode: ATimesNumericBinaryExpIR)
        = CsTimesExpression(irNode.left.translate, irNode.right.translate)
}
