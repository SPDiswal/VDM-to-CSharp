package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.Cardinalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class HeadTranslations : ExpressionTranslationRuleSpek<AHeadUnaryExpIR>(Heads)
{
    init
    {
        "hd [ ]" describesThat
            head(emptySequenceEnumeration(intType)) becomes
            "new List<int>().First()"

        "hd [ 1, 2, 3 ]" describesThat
            head(sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral)) becomes
            "new List<int> { 1, 2, 3 }.First()"
    }
}
