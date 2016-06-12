package org.overture.codegen.vdm2cs.translations.expressions

import org.overture.codegen.ir.SExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface ExpressionTranslationRule<TIrNode : SExpIR> : TranslationRule<TIrNode, CsExpression>
