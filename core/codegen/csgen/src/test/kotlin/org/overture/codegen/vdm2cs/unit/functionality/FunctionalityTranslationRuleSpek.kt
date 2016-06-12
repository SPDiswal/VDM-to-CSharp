package org.overture.codegen.vdm2cs.unit.functionality

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsMethodDeclaration
import org.overture.codegen.vdm2cs.translations.functionality.FunctionalityTranslationRule

abstract class FunctionalityTranslationRuleSpek<TIrNode : SDeclIR>(
    translationRule: FunctionalityTranslationRule<TIrNode>) :
    DefinitionTranslationRuleSpek<TIrNode, CsMethodDeclaration>(translationRule)
