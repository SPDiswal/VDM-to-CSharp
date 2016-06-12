package org.overture.codegen.vdm2cs.unit.expressions

import org.overture.codegen.ir.SExpIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.parseExpression

abstract class ExpressionTranslationRuleSpek<TIrNode : SExpIR>(translationRule: ExpressionTranslationRule<TIrNode>) :
    TranslationRuleSpek<TIrNode, CsExpression>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(csExpressionSyntax: String)
        = this becomes parseExpression(csExpressionSyntax)
}
