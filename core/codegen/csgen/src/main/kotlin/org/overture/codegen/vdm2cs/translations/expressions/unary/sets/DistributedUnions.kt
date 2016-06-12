package org.overture.codegen.vdm2cs.translations.expressions.unary.sets

import org.overture.codegen.ir.expressions.ADistUnionUnaryExpIR
import org.overture.codegen.ir.types.SSetTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object DistributedUnions : UnaryExpressionTranslationRule<ADistUnionUnaryExpIR>
{
    override fun translate(irNode: ADistUnionUnaryExpIR)
        = parseExpression("(${irNode.exp.inline})" +
                          ".Cast<IEnumerable<${(irNode.type as SSetTypeIR).setOf.inline}>>()" +
                          ".Aggregate(Enumerable.Union)" +
                          ".ToHashSet()")
}
