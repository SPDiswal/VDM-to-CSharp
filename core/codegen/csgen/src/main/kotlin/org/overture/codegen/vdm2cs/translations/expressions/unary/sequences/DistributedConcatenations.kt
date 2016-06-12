package org.overture.codegen.vdm2cs.translations.expressions.unary.sequences

import org.overture.codegen.ir.expressions.ADistConcatUnaryExpIR
import org.overture.codegen.ir.types.SSeqTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object DistributedConcatenations : UnaryExpressionTranslationRule<ADistConcatUnaryExpIR>
{
    override fun translate(irNode: ADistConcatUnaryExpIR)
        = parseExpression("(${irNode.exp.inline})" +
                          ".Cast<IEnumerable<${(irNode.type as SSeqTypeIR).seqOf.inline}>>()" +
                          ".Aggregate(Enumerable.Concat)" +
                          ".ToList()")
}
