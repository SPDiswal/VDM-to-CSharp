package org.overture.codegen.vdm2cs.unit.expressions.binary.logical

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.logical.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ExclusiveDisjunctionTranslations : ExpressionTranslationRuleSpek<AXorBoolBinaryExpIR>(ExclusiveDisjunctions)
{
    init
    {
        "true xor false" describesThat
            (true.toLiteral xor false.toLiteral) becomes
            "true ^ false"

        "true xor true" describesThat
            (true.toLiteral xor true.toLiteral) becomes
            "true ^ true"

        "disjunct xor disjunct" describesThat
            (identifier("disjunct", boolType) xor identifier("disjunct", boolType)) becomes
            "disjunct ^ disjunct"

        "(true xor false) xor true" describesThat
            ((true.toLiteral xor false.toLiteral) xor true.toLiteral) becomes
            "(true ^ false) ^ true"

        "true xor (false xor true)" describesThat
            (true.toLiteral xor (false.toLiteral xor true.toLiteral)) becomes
            "true ^ (false ^ true)"
    }
}
