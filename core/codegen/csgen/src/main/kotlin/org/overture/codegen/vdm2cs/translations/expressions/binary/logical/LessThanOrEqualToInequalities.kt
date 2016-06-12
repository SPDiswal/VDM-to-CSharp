package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.ALessEqualNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsLessThanOrEqualToExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object LessThanOrEqualToInequalities : BinaryExpressionTranslationRule<ALessEqualNumericBinaryExpIR>
{
    override fun translate(irNode: ALessEqualNumericBinaryExpIR)
        = CsLessThanOrEqualToExpression(irNode.left.translate, irNode.right.translate)
}
