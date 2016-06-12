package org.overture.codegen.vdm2cs.translations.expressions.unary.mappings

import org.overture.codegen.ir.expressions.ADistMergeUnaryExpIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object DistributedMerges : UnaryExpressionTranslationRule<ADistMergeUnaryExpIR>
{
    override fun translate(irNode: ADistMergeUnaryExpIR): CsExpression
    {
        val domainType = (irNode.type as SMapTypeIR).from.inline
        val rangeType = (irNode.type as SMapTypeIR).to.inline

        return parseExpression("(${irNode.exp.inline})" +
                               ".Cast<IEnumerable<KeyValuePair<$domainType, $rangeType>>>()" +
                               ".Aggregate(Enumerable.Concat)" +
                               ".ToDictionary()")
    }
}
