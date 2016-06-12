package org.overture.codegen.vdm2cs.translations.expressions.literals

import org.overture.codegen.ir.expressions.SLiteralExpIR
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

interface LiteralTranslationRule<TIrNode : SLiteralExpIR> : ExpressionTranslationRule<TIrNode>
