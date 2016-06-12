package org.overture.codegen.vdm2cs.unit.expressions.literals

import org.overture.codegen.ir.expressions.ACharLiteralExpIR
import org.overture.codegen.vdm2cs.translations.expressions.literals.CharacterLiterals
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.toLiteral

class CharacterLiteralTranslations : ExpressionTranslationRuleSpek<ACharLiteralExpIR>(CharacterLiterals)
{
    init
    {
        "'A'" describesThat
            'A'.toLiteral becomes
            "'A'"

        "'\"'" describesThat
            '"'.toLiteral becomes
            "'\"'"

        // TODO Escape sequences
    }
}
