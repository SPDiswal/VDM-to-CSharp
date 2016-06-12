package org.overture.codegen.vdm2cs.translations.expressions.unary

import org.overture.codegen.ir.expressions.SUnaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

interface UnaryExpressionTranslationRule<TIrNode : SUnaryExpIR> : ExpressionTranslationRule<TIrNode>
