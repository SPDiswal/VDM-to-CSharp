package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.AOrBoolBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.binary.CsOrExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object Disjunctions : BinaryExpressionTranslationRule<AOrBoolBinaryExpIR>
{
    override fun translate(irNode: AOrBoolBinaryExpIR)
        = CsOrExpression(irNode.left.translate, irNode.right.translate)
}
