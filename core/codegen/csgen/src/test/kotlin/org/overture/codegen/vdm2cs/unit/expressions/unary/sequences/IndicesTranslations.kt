package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.AIndicesUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.Indices
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IndicesTranslations : ExpressionTranslationRuleSpek<AIndicesUnaryExpIR>(Indices)
{
    init
    {
        "inds [ ]" describesThat
            indices(emptySequenceEnumeration(intType)) becomes
            "Enumerable.Range(1, new List<int>().Count).ToHashSet()"

        "inds [ 1, 2, 3 ]" describesThat
            indices(sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral)) becomes
            "Enumerable.Range(1, new List<int> { 1, 2, 3 }.Count).ToHashSet()"
    }
}
