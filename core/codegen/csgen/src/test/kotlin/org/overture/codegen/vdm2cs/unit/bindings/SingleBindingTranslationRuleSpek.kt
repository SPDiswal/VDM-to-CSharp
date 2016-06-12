package org.overture.codegen.vdm2cs.unit.bindings

import org.overture.codegen.ir.SBindIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.expressions.linq.CsQueryExpression
import org.overture.codegen.vdm2cs.translations.bindings.SingleBindingTranslationRule
import org.overture.codegen.vdm2cs.utilities.parseQueryExpression

abstract class SingleBindingTranslationRuleSpek<TIrNode : SBindIR>(
    translationRule: SingleBindingTranslationRule<TIrNode>) :
    TranslationRuleSpek<TIrNode, CsQueryExpression>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(csQueryExpressionSyntax: String)
        = this becomes parseQueryExpression(csQueryExpressionSyntax)
}
