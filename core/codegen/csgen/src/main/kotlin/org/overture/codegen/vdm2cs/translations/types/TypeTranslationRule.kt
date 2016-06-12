package org.overture.codegen.vdm2cs.translations.types

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface TypeTranslationRule<TIrNode : STypeIR> : TranslationRule<TIrNode, CsTypeIdentifier>
