package org.overture.codegen.vdm2cs.unit.expressions.unary

import org.overture.codegen.ir.expressions.AIsolationUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.unary.Isolations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsolationTranslations : ExpressionTranslationRuleSpek<AIsolationUnaryExpIR>(Isolations)
{
    init
    {
        "1" describesThat
            isolate(1.toLiteral) becomes
            "1"

        "1 + 1" describesThat
            isolate(1.toLiteral plus 1.toLiteral) becomes
            "1 + 1"

        "number" describesThat
            isolate(identifier("number", intType)) becomes
            "number"
    }
}
