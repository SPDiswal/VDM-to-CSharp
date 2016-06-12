package org.overture.codegen.vdm2cs.unit.types

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier
import org.overture.codegen.vdm2cs.utilities.parseType
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule

abstract class TypeTranslationRuleSpek<TIrNode : STypeIR>(translationRule: TypeTranslationRule<TIrNode>) :
    TranslationRuleSpek<TIrNode, CsTypeIdentifier>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(csTypeSyntax: String) = this becomes parseType(csTypeSyntax)
}
