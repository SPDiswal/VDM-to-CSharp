package org.overture.codegen.vdm2cs.translations.expressions.binary.sets

import org.overture.codegen.ir.expressions.ASetIntersectBinaryExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Intersections : BinaryExpressionTranslationRule<ASetIntersectBinaryExpIR>
{
    override fun translate(irNode: ASetIntersectBinaryExpIR)
        = parseExpression("(${irNode.left.inline}).Intersect(${irNode.right.inline}).ToHashSet()")
}
