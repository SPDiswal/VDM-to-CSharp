package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.ANullExpIR
import org.overture.codegen.vdm2cs.translations.expressions.literals.NullLiterals
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class NullLiteralTranslations : ExpressionTranslationRuleSpek<ANullExpIR>(NullLiterals)
{
    init
    {
        "nil" describesThat
            nil(optional(boolType)) becomes
            "null"

        "nil" describesThat
            nil(optional(intType)) becomes
            "null"
    }
}
