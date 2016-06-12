package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface BindingTranslationRule<TIrNode : SBindIR> : TranslationRule<TIrNode, CsExpression>
