package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.AModNumericBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Modulus : BinaryExpressionTranslationRule<AModNumericBinaryExpIR>
{
    override fun translate(irNode: AModNumericBinaryExpIR)
        = parseExpression("(${irNode.left.inline}).Modulo(${irNode.right.inline})")
}
