package org.overture.codegen.vdm2cs.translations.expressions.binary

import org.overture.codegen.ir.expressions.SBinaryExpIR
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

interface BinaryExpressionTranslationRule<TIrNode : SBinaryExpIR> : ExpressionTranslationRule<TIrNode>
