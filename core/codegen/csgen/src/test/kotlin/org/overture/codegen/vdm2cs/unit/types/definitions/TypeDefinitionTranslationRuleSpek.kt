package org.overture.codegen.vdm2cs.unit.types.definitions

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.translations.types.definitions.TypeDefinitionTranslationRule
import org.overture.codegen.vdm2cs.unit.functionality.DefinitionTranslationRuleSpek

abstract class TypeDefinitionTranslationRuleSpek<TIrNode : SDeclIR>(
    translationRule: TypeDefinitionTranslationRule<TIrNode>) :
    DefinitionTranslationRuleSpek<TIrNode, CsClassDeclaration>(translationRule)
