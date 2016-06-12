package org.overture.codegen.vdm2cs.unit.functionality

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsDeclaration
import org.overture.codegen.vdm2cs.parser.builders.CsBuilder
import org.overture.codegen.vdm2cs.translations.functionality.DefinitionTranslationRule

abstract class DefinitionTranslationRuleSpek<TIrNode : SDeclIR, TCsNode : CsDeclaration>(
    translationRule: DefinitionTranslationRule<TIrNode, TCsNode>) :
    TranslationRuleSpek<TIrNode, TCsNode>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(expectedCsAstBuilder: CsBuilder<TCsNode>)
        = this becomes expectedCsAstBuilder.ast
}
