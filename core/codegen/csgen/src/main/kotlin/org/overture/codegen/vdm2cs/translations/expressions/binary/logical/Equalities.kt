package org.overture.codegen.vdm2cs.translations.expressions.binary.logical

import org.overture.codegen.ir.expressions.AEqualsBinaryExpIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Equalities : BinaryExpressionTranslationRule<AEqualsBinaryExpIR>
{
    override fun translate(irNode: AEqualsBinaryExpIR): CsExpression
    {
        val left = irNode.left.inline
        val right = irNode.right.inline

        return when (irNode.left.type)
        {
            is ASetSetTypeIR ->
            {
                when
                {
                    irNode.left.isEmptySet  -> parseExpression("!($right).Any()")
                    irNode.right.isEmptySet -> parseExpression("!($left).Any()")
                    else                    -> parseExpression("($left).SetEquals($right)")
                }
            }

            is ASeqSeqTypeIR ->
            {
                when
                {
                    irNode.left.type.isStringType -> parseExpression("($left) == ($right)")
                    irNode.left.isEmptySequence   -> parseExpression("!($right).Any()")
                    irNode.right.isEmptySequence  -> parseExpression("!($left).Any()")
                    else                          -> parseExpression("($left).SequenceEqual($right)")
                }
            }

            is AMapMapTypeIR ->
            {
                when
                {
                    irNode.left.isEmptyMapping  -> parseExpression("!($right).Any()")
                    irNode.right.isEmptyMapping -> parseExpression("!($left).Any()")
                    else                        -> parseExpression("($left).DictionaryEquals($right)")
                }
            }

            is ATupleTypeIR,
            is ARecordTypeIR -> parseExpression("object.Equals($left, $right)")

            else             -> parseExpression("($left) == ($right)")
        }
    }
}
