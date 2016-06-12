package org.overture.codegen.vdm2cs.translations.functionality

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsDeclaration
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface DefinitionTranslationRule<TIrNode : SDeclIR, TCsNode : CsDeclaration> : TranslationRule<TIrNode, TCsNode>
