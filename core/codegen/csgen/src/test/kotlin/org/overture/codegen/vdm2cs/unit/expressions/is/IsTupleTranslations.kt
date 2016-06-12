package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.ATupleIsExpIR
import org.overture.codegen.vdm2cs.translations.expressions.`is`.IsTuples
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsTupleTranslations : ExpressionTranslationRuleSpek<ATupleIsExpIR>(IsTuples)
{
    init
    {
        "is_(mk_(1, true), int * bool)" describesThat
            isTuple(mkTuple(1.toLiteral, true.toLiteral), tupleOf(intType, boolType)) becomes
            "Let(() => {" +
            "    var _ = Tuple.Create(1, true) as Tuple<int, bool>;" +
            "    return _ != null;" +
            "})"

        "is_(a, nat * nat1)" describesThat
            isTuple(identifier("a", tupleOf(natType, nat1Type)), tupleOf(natType, nat1Type)) becomes
            "Let(() => {" +
            "    var _ = a as Tuple<int, int>;" +
            "    return _ != null && _.Item1 >= 0 && _.Item2 > 0;" +
            "})"
    }
}
