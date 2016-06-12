package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.Lengths
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.Cardinalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class LengthTranslations : ExpressionTranslationRuleSpek<ALenUnaryExpIR>(Lengths)
{
    init
    {
        "len [ ]" describesThat
            length(emptySequenceEnumeration(intType)) becomes
            "new List<int>().Count"

        "len [ 1, 2, 3 ]" describesThat
            length(sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral)) becomes
            "new List<int> { 1, 2, 3 }.Count"
    }
}
