package org.overture.codegen.vdm2cs.translations.expressions.unary.sets

import org.overture.codegen.ir.expressions.ADistIntersectUnaryExpIR
import org.overture.codegen.ir.types.SSetTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object DistributedIntersections : UnaryExpressionTranslationRule<ADistIntersectUnaryExpIR>
{
    override fun translate(irNode: ADistIntersectUnaryExpIR)
        = parseExpression("(${irNode.exp.inline})" +
                          ".Cast<IEnumerable<${(irNode.type as SSetTypeIR).setOf.inline}>>()" +
                          ".Aggregate(Enumerable.Intersect)" +
                          ".ToHashSet()")
}
