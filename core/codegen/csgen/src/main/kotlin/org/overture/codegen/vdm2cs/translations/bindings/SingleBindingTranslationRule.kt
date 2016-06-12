package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.SBindIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.linq.CsQueryExpression
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface SingleBindingTranslationRule<TIrNode : SBindIR> : TranslationRule<TIrNode, CsQueryExpression>
