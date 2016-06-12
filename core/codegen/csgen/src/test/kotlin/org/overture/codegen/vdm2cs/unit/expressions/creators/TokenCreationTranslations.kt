package org.overture.codegen.vdm2cs.unit.expressions.creators

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.creators.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class TokenCreationTranslations : ExpressionTranslationRuleSpek<AMkBasicExpIR>(TokenCreations)
{
    init
    {
        "mk_Token(1)" describesThat
            mkToken(1.toLiteral) becomes
            "Token.Create(1)"

        "mk_Token(\"ABC\")" describesThat
            mkToken("ABC".toLiteral) becomes
            "Token.Create(\"ABC\")"
    }
}
