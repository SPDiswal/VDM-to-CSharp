package org.overture.codegen.vdm2cs.unit.types.other

import org.overture.codegen.ir.types.ATemplateTypeIR
import org.overture.codegen.vdm2cs.translations.types.other.TemplateTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.templateType

class TemplateTypeTranslations : TypeTranslationRuleSpek<ATemplateTypeIR>(TemplateTypes)
{
    init
    {
        "@$nextPlaceholder" describesThat
            templateType(placeholder) becomes
            "T$placeholder"

        "@$nextPlaceholder" describesThat
            templateType(placeholder) becomes
            "T$placeholder"

        "@$nextLowerCasePlaceholder" describesThat
            templateType(lowerCasePlaceholder) becomes
            "T$placeholder"

        "@T$nextPlaceholder" describesThat
            templateType("T$placeholder") becomes
            "T$placeholder"

        "@T$nextLowerCasePlaceholder" describesThat
            templateType("T$lowerCasePlaceholder") becomes
            "TT$lowerCasePlaceholder"
    }
}
