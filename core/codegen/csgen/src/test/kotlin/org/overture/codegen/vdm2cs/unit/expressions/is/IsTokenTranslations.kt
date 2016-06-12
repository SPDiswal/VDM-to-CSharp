package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.expressions.`is`.*
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IsTokenTranslations : ExpressionTranslationRuleSpek<ATokenIsExpIR>(IsTokens)
{
    init
    {
        "is_token(mk_Token(1))" describesThat
            isToken(mkToken(1.toLiteral)) becomes
            "Token.Create(1) is Token"

        "is_token(a)" describesThat
            isToken(identifier("a", tokenType)) becomes
            "a is Token"
    }
}
