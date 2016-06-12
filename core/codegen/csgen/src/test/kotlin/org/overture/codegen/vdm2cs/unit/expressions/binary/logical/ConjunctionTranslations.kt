package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.AAndBoolBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.Conjunctions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ConjunctionTranslations : ExpressionTranslationRuleSpek<AAndBoolBinaryExpIR>(Conjunctions)
{
    init
    {
        "true and false" describesThat
            (true.toLiteral and false.toLiteral) becomes
            "true && false"

        "true and true" describesThat
            (true.toLiteral and true.toLiteral) becomes
            "true && true"

        "conjunct and conjunct" describesThat
            (identifier("conjunct", boolType) and identifier("conjunct", boolType)) becomes
            "conjunct && conjunct"

        "(true and false) and true" describesThat
            ((true.toLiteral and false.toLiteral) and true.toLiteral) becomes
            "(true && false) && true"

        "true and (false and true)" describesThat
            (true.toLiteral and (false.toLiteral and true.toLiteral)) becomes
            "true && (false && true)"
    }
}
