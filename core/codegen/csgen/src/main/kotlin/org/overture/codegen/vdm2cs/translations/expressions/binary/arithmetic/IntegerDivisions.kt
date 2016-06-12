package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.AIntDivNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsDivideExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object IntegerDivisions : BinaryExpressionTranslationRule<AIntDivNumericBinaryExpIR>
{
    override fun translate(irNode: AIntDivNumericBinaryExpIR): CsExpression
        = CsDivideExpression(irNode.left.translate, irNode.right.translate)
}
