package org.overture.codegen.vdm2cs.translations.expressions.binary.sequences

import org.overture.codegen.ir.expressions.ASeqConcatBinaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Concatenations : BinaryExpressionTranslationRule<ASeqConcatBinaryExpIR>
{
    override fun translate(irNode: ASeqConcatBinaryExpIR) =
        if (irNode.left.type.isStringType && irNode.right.type.isStringType)
            parseExpression("(${irNode.left.inline}) + (${irNode.right.inline})")
        else
            parseExpression("(${irNode.left.inline}).Concat(${irNode.right.inline}).ToList()")
}
