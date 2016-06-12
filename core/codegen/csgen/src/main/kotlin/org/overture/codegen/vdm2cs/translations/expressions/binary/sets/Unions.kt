package org.overture.codegen.vdm2cs.translations.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetUnionBinaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Unions : BinaryExpressionTranslationRule<ASetUnionBinaryExpIR>
{
    override fun translate(irNode: ASetUnionBinaryExpIR)
        = parseExpression("(${irNode.left.inline}).Union(${irNode.right.inline}).ToHashSet()")
}
