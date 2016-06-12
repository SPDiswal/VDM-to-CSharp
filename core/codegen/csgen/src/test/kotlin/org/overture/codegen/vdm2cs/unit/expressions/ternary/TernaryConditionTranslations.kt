package org.overture.codegen.vdm2cs.unit.expressions.ternary

import org.overture.codegen.ir.expressions.ATernaryIfExpIR
import org.overture.codegen.vdm2cs.translations.expressions.ternary.TernaryConditions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class TernaryConditionTranslations : ExpressionTranslationRuleSpek<ATernaryIfExpIR>(TernaryConditions)
{
    init
    {
        "if true then true else false" describesThat
            ternaryIf(condition = true.toLiteral,
                      thenExpression = true.toLiteral,
                      elseExpression = false.toLiteral) becomes
            "true ? true : false"

        "if 1 + 1 = 2 then 1 else 2" describesThat
            ternaryIf(condition = (1.toLiteral plus 1.toLiteral) isEqualTo 2.toLiteral,
                      thenExpression = 1.toLiteral,
                      elseExpression = 2.toLiteral) becomes
            "1 + 1 == 2 ? 1 : 2"

        "if condition then truth else falsehood" describesThat
            ternaryIf(condition = identifier("condition", boolType),
                      thenExpression = identifier("truth", boolType),
                      elseExpression = identifier("falsehood", boolType)) becomes
            "condition ? truth : falsehood"

        "if if true then true else false then true else false" describesThat
            ternaryIf(condition = ternaryIf(condition = true.toLiteral,
                                            thenExpression = true.toLiteral,
                                            elseExpression = false.toLiteral),
                      thenExpression = true.toLiteral,
                      elseExpression = false.toLiteral) becomes
            "(true ? true : false) ? true : false"

        "if true then true else if false then true else false" describesThat
            ternaryIf(condition = true.toLiteral,
                      thenExpression = true.toLiteral,
                      elseExpression = ternaryIf(condition = false.toLiteral,
                                                 thenExpression = true.toLiteral,
                                                 elseExpression = false.toLiteral)) becomes
            "true ? true : false ? true : false"
    }
}
