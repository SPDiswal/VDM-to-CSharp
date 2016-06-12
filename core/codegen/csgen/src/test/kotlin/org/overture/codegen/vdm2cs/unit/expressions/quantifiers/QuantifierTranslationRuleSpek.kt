package org.overture.codegen.vdm2cs.unit.expressions.quantifiers

import org.overture.codegen.ir.expressions.SQuantifierExpIR
import org.overture.codegen.vdm2cs.translations.expressions.quantifiers.QuantifierTranslationRule
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek

abstract class QuantifierTranslationRuleSpek<TIrNode : SQuantifierExpIR>(
    translationRule: QuantifierTranslationRule<TIrNode>) :
    ExpressionTranslationRuleSpek<TIrNode>(translationRule)
