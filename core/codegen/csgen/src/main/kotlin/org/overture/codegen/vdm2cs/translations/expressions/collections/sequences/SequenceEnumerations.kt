package org.overture.codegen.vdm2cs.translations.expressions.collections.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SequenceEnumerations : ExpressionTranslationRule<AEnumSeqExpIR>
{
    override fun translate(irNode: AEnumSeqExpIR): CsExpression
    {
        val elementType = (irNode.type as SSeqTypeIR).seqOf
        val formattedElementType = elementType.inline

        if (elementType is ACharBasicTypeIR && irNode.members.all { it is ACharLiteralExpIR })
        {
            val stringLiteral = irNode.members.map { (it as ACharLiteralExpIR).value }.joinToString("")
            return parseExpression("\"${stringLiteral.withEscapeSequences}\"")
        }

        when (irNode.members.size)
        {
            0    -> return parseExpression("new List<$formattedElementType>()")
            else -> return parseExpression("new List<$formattedElementType> { ${irNode.members.joinExpressions} }")
        }
    }
}
