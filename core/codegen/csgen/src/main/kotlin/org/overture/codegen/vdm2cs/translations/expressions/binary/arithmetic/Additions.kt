package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.APlusNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsPlusExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Additions : BinaryExpressionTranslationRule<APlusNumericBinaryExpIR>
{
    override fun translate(irNode: APlusNumericBinaryExpIR)
        = CsPlusExpression(irNode.left.translate, irNode.right.translate)
}
