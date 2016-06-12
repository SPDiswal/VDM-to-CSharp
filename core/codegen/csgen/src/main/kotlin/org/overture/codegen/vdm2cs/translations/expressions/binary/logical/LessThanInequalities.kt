package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.ALessNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsLessThanExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object LessThanInequalities : BinaryExpressionTranslationRule<ALessNumericBinaryExpIR>
{
    override fun translate(irNode: ALessNumericBinaryExpIR)
        = CsLessThanExpression(irNode.left.translate, irNode.right.translate)
}
