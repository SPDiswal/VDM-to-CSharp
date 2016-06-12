package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.AGreaterEqualNumericBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsGreaterThanOrEqualToExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object GreaterThanOrEqualToInequalities : BinaryExpressionTranslationRule<AGreaterEqualNumericBinaryExpIR>
{
    override fun translate(irNode: AGreaterEqualNumericBinaryExpIR)
        = CsGreaterThanOrEqualToExpression(irNode.left.translate, irNode.right.translate)
}
