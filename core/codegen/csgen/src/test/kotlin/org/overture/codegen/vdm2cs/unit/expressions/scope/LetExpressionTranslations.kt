package org.overture.codegen.vdm2cs.unit.expressions.scope

import org.overture.codegen.ir.expressions.ALetDefExpIR
import org.overture.codegen.vdm2cs.translations.expressions.scope.LetExpressions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class LetExpressionTranslations : ExpressionTranslationRuleSpek<ALetDefExpIR>(LetExpressions)
{
    init
    {
        "let x = 1 in true" describesThat
            let(declarations = listOf(variable("x", intType, 1.toLiteral)),
                expression = true.toLiteral) becomes
            "Let(() => {" +
            "    var x = 1;" +
            "    return true;" +
            "})"

        "let y = 2, z = 3 in y + z" describesThat
            let(declarations = listOf(variable("y", intType, 2.toLiteral),
                                      variable("z", intType, 3.toLiteral)),
                expression = identifier("y", intType) plus identifier("z", intType)) becomes
            "Let(() => {" +
            "    var y = 2;" +
            "    var z = 3;" +
            "    return y + z;" +
            "})"
    }
}
