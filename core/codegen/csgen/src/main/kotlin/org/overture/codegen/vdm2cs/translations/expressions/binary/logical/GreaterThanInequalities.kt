package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.AGreaterNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsGreaterThanExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object GreaterThanInequalities : BinaryExpressionTranslationRule<AGreaterNumericBinaryExpIR>
{
    override fun translate(irNode: AGreaterNumericBinaryExpIR)
        = CsGreaterThanExpression(irNode.left.translate, irNode.right.translate)
}
