package org.overture.codegen.vdm2cs.translations.functionality

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsMethodDeclaration

interface FunctionalityTranslationRule<TIrNode : SDeclIR> : DefinitionTranslationRule<TIrNode, CsMethodDeclaration>
