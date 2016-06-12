package org.overture.codegen.vdm2cs.unit.bindings

import org.overture.codegen.ir.SBindIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.bindings.BindingTranslationRule
import org.overture.codegen.vdm2cs.utilities.parseExpression

abstract class BindingTranslationRuleSpek<TIrNode : SBindIR>(translationRule: BindingTranslationRule<TIrNode>) :
    TranslationRuleSpek<TIrNode, CsExpression>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(csExpressionSyntax: String)
        = this becomes parseExpression(csExpressionSyntax)
}
