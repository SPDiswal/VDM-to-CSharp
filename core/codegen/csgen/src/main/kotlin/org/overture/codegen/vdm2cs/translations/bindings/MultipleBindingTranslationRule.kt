package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.SMultipleBindIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.linq.CsQueryExpression
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface MultipleBindingTranslationRule<TIrNode : SMultipleBindIR> : TranslationRule<TIrNode, CsQueryExpression>
