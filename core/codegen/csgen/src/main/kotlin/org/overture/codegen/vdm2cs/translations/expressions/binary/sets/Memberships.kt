package org.overture.codegen.vdm2cs.translations.expressions.binary.sets

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Memberships : BinaryExpressionTranslationRule<AInSetBinaryExpIR>
{
    override fun translate(irNode: AInSetBinaryExpIR): CsExpression
    {
        val rightNode = irNode.right
        val left = irNode.left.inline
        val right = irNode.right.inline

        return when (rightNode)
        {
            is AElemsUnaryExpIR     -> parseExpression("(${rightNode.exp.inline}).Contains($left)")
            is AMapDomainUnaryExpIR -> parseExpression("(${rightNode.exp.inline}).ContainsKey($left)")
            is AMapRangeUnaryExpIR  -> parseExpression("(${rightNode.exp.inline}).ContainsValue($left)")
            else                    -> parseExpression("($right).Contains($left)")
        }
    }
}
