package org.overture.codegen.vdm2cs.unit.functionality

import org.overture.codegen.ir.declarations.AModuleDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.translations.functionality.DefinitionTranslationRule

abstract class SpecificationTranslationRuleSpek(
    translationRule: DefinitionTranslationRule<AModuleDeclIR, CsClassDeclaration>) :
    DefinitionTranslationRuleSpek<AModuleDeclIR, CsClassDeclaration>(translationRule)
