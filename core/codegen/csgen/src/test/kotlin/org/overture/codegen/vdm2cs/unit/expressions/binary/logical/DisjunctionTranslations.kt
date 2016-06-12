package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.AOrBoolBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.Disjunctions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class DisjunctionTranslations : ExpressionTranslationRuleSpek<AOrBoolBinaryExpIR>(Disjunctions)
{
    init
    {
        "true or false" describesThat
            (true.toLiteral or false.toLiteral) becomes
            "true || false"

        "true or true" describesThat
            (true.toLiteral or true.toLiteral) becomes
            "true || true"

        "disjunct or disjunct" describesThat
            (identifier("disjunct", boolType) or identifier("disjunct", boolType)) becomes
            "disjunct || disjunct"

        "(true or false) or true" describesThat
            ((true.toLiteral or false.toLiteral) or true.toLiteral) becomes
            "(true || false) || true"

        "true or (false or true)" describesThat
            (true.toLiteral or (false.toLiteral or true.toLiteral)) becomes
            "true || (false || true)"
    }
}
