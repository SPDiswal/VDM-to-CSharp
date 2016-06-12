package org.overture.codegen.vdm2cs.unit.bindings

import org.overture.codegen.ir.SMultipleBindIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.expressions.linq.CsQueryExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.bindings.MultipleBindingTranslationRule
import org.overture.codegen.vdm2cs.utilities.parseQueryExpression

abstract class MultipleBindingTranslationRuleSpek<TIrNode : SMultipleBindIR>(
    translationRule: MultipleBindingTranslationRule<TIrNode>) :
    TranslationRuleSpek<TIrNode, CsQueryExpression>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(csQueryExpressionSyntax: String)
        = this becomes parseQueryExpression(csQueryExpressionSyntax)
}
