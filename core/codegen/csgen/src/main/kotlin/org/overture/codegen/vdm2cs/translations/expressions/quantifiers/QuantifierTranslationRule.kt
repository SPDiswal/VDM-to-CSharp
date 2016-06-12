package org.overture.codegen.vdm2cs.translations.expressions.quantifiers

import org.overture.codegen.ir.expressions.SQuantifierExpIR
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

interface QuantifierTranslationRule<TIrNode : SQuantifierExpIR> : ExpressionTranslationRule<TIrNode>
