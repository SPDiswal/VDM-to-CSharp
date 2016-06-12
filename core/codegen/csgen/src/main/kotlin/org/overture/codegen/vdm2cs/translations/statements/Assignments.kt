package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.*
import org.overture.codegen.ir.expressions.*
import org.overture.codegen.ir.statements.*
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.utilities.*

object Assignments : StatementTranslationRule<AAssignmentStmIR>
{
    override fun translate(irNode: AAssignmentStmIR): CsStatement
    {
        val targetType = irNode.target.type
        val expression = irNode.exp
        val target = inlineStateDesignator(irNode.target)

        if (expression is SBinaryExpIR)
        {
            val argument = when (target)
            {
                expression.left.inline  -> expression.right
                expression.right.inline -> expression.left
                else                    -> null
            }

            if (argument != null)
            {
                when (targetType)
                {
                    is SSetTypeIR -> return parseStatement(handleSetManipulations(target, expression, argument))
                    is SSeqTypeIR -> return parseStatement(handleSequenceManipulations(target, expression, argument))
                    is SMapTypeIR -> return parseStatement(handleMappingManipulations(target, expression, argument))
                }
            }
        }

        return parseStatement("$target = ${expression.inline};")
    }

    private fun handleSetManipulations(target: String, expression: SExpIR, argument: SExpIR) = when
    {
        expression is ASetUnionBinaryExpIR                                       -> when
        {
            argument.isSingletonSet -> "$target.Add(${argument.getSingleElement.inline});"
            else                    -> "$target.UnionWith(${argument.inline});"
        }

        expression is ASetIntersectBinaryExpIR                                   ->
            "$target.IntersectWith(${argument.inline});"

        expression is ASetDifferenceBinaryExpIR && argument === expression.right -> when
        {
            argument.isSingletonSet -> "$target.Remove(${argument.getSingleElement.inline});"
            else                    -> "$target.ExceptWith(${argument.inline});"
        }

        else                                                                     ->
            "$target = ${expression.inline};"
    }

    private fun handleSequenceManipulations(target: String, expression: SExpIR, argument: SExpIR) = when
    {
        expression is ASeqConcatBinaryExpIR && argument === expression.left -> when
        {
            argument.isSingletonSequence -> "$target.Insert(${argument.getSingleElement.inline}, 0);"
            else                         -> "$target.InsertRange(${argument.inline}, 0);"
        }

        expression is ASeqConcatBinaryExpIR && argument === expression.right -> when
        {
            argument.isSingletonSequence -> "$target.Add(${argument.getSingleElement.inline});"
            else                         -> "$target.AddRange(${argument.inline});"
        }

        else                                -> "$target = ${expression.inline};"
    }

    private fun handleMappingManipulations(target: String, expression: SExpIR, argument: SExpIR) = when
    {
        expression is AMapUnionBinaryExpIR    -> when
        {
            argument.isSingletonMapping ->
            {
                val maplet = argument.getSingleElement as AMapletExpIR
                "$target.Add(${maplet.left.inline}, ${maplet.right.inline});"
            }
            else                        -> "$target = ${expression.inline};"
        }

        expression is AMapOverrideBinaryExpIR && argument === expression.right -> when
        {
            argument.isSingletonMapping ->
            {
                val maplet = argument.getSingleElement as AMapletExpIR
                "$target[${maplet.left.inline}] = ${maplet.right.inline};"
            }
            else                        -> "$target = ${expression.inline};"
        }

        else                                  -> "$target = ${expression.inline};"
    }

    private fun inlineStateDesignator(receiver: SStateDesignatorIR): String = when (receiver)
    {
        is AIdentifierStateDesignatorIR -> when (receiver.isLocal)
        {
            true  -> parseExpression(receiver.name.toLowerCamelCase())
            false -> parseExpression("State.${receiver.name.toUpperCamelCase()}")
        }

        is AFieldStateDesignatorIR      -> parseExpression(
            "${inlineStateDesignator(receiver.`object`)}.${receiver.field.toUpperCamelCase()}"
        )

        is AMapSeqStateDesignatorIR     -> parseExpression(
            "${inlineStateDesignator(receiver.mapseq)}[${receiver.exp.inline}]"
        )

        else                            -> throw UnsupportedTranslationException(receiver)
    }.format
}
