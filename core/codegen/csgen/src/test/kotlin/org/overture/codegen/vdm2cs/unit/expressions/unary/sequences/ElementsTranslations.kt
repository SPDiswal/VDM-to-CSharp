package org.overture.codegen.vdm2cs.unit.expressions.unary.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.logical.Negations
import org.overture.codegen.vdm2cs.translations.expressions.unary.sequences.*
import org.overture.codegen.vdm2cs.translations.expressions.unary.sets.Cardinalities
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ElementsTranslations : ExpressionTranslationRuleSpek<AElemsUnaryExpIR>(Elements)
{
    init
    {
        "elems [ ]" describesThat
            elements(emptySequenceEnumeration(intType)) becomes
            "new List<int>().ToHashSet()"

        "elems [ 1, 2, 3 ]" describesThat
            elements(sequenceEnumeration(1.toLiteral, 2.toLiteral, 3.toLiteral)) becomes
            "new List<int> { 1, 2, 3 }.ToHashSet()"
    }
}
