package org.overture.codegen.vdm2cs.unit.expressions.creators

import org.overture.codegen.ir.expressions.ATupleExpIR
import org.overture.codegen.vdm2cs.translations.expressions.creators.TupleCreations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class TupleCreationTranslations : ExpressionTranslationRuleSpek<ATupleExpIR>(TupleCreations)
{
    init
    {
        "mk_(1, 2)" describesThat
            mkTuple(1.toLiteral, 2.toLiteral) becomes
            "Tuple.Create(1, 2)"

        "mk_(true, 42, \"ABC\")" describesThat
            mkTuple(true.toLiteral, 42.toLiteral, "ABC".toLiteral) becomes
            "Tuple.Create(true, 42, \"ABC\")"
    }
}
