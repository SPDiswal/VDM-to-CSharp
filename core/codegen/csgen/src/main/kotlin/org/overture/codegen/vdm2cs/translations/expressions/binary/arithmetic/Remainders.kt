package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.ARemNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsModulusExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Remainders : BinaryExpressionTranslationRule<ARemNumericBinaryExpIR>
{
    override fun translate(irNode: ARemNumericBinaryExpIR): CsExpression
        = CsModulusExpression(irNode.left.translate, irNode.right.translate)
}
