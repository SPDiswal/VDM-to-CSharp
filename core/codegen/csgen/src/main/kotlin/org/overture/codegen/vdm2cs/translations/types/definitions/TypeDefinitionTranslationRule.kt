package org.overture.codegen.vdm2cs.translations.types.definitions

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.translations.functionality.DefinitionTranslationRule

interface TypeDefinitionTranslationRule<TIrNode : SDeclIR> : DefinitionTranslationRule<TIrNode, CsClassDeclaration>
