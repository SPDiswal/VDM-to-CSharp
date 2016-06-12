package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.Cardinalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class TailTranslations : ExpressionTranslationRuleSpek<ATailUnaryExpIR>(Tails)
{
    init
    {
        "tl [ ]" describesThat
            tail(emptySequenceEnumeration(intType)) becomes
            "new List<int>().Skip(1).ToList()"

        "tl [ 1, 2, 3 ]" describesThat
            tail(sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral)) becomes
            "new List<int> { 1, 2, 3 }.Skip(1).ToList()"
    }
}
