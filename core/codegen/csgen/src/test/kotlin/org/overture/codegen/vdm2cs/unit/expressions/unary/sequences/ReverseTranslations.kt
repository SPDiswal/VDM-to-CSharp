package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.AReverseUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.Reverses
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ReverseTranslations : ExpressionTranslationRuleSpek<AReverseUnaryExpIR>(Reverses)
{
    init
    {
        "reverse [ ]" describesThat
            reverse(emptySequenceEnumeration(intType)) becomes
            "new List<int>().Reverse().ToList()"

        "reverse [ 1, 2, 3 ]" describesThat
            reverse(sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral)) becomes
            "new List<int> { 1, 2, 3 }.Reverse().ToList()"
    }
}
