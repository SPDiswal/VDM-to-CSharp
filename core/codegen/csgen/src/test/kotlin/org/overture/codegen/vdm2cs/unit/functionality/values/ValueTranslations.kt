package org.overture.codegen.vdm2cs.unit.functionality.values

import org.overture.codegen.ir.declarations.AFieldDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsPropertyDeclaration
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.values.Values
import org.overture.codegen.vdm2cs.unit.functionality.DefinitionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ValueTranslations : DefinitionTranslationRuleSpek<AFieldDeclIR, CsPropertyDeclaration>(Values)
{
    init
    {
        "$nextPlaceholder: int = 1" describesThat
            valueDeclaration(name = placeholder, type = intType, value = 1.toLiteral) becomes
            publicStaticInitialisedReadonlyAutoProperty(name = placeholder, type = "int", initialiser = "1")

        "$nextLowerCasePlaceholder: seq of char = \"ABC\"" describesThat
            valueDeclaration(name = lowerCasePlaceholder, type = seqOf(charType), value = "ABC".toLiteral) becomes
            publicStaticInitialisedReadonlyAutoProperty(name = placeholder, type = "string", initialiser = "\"ABC\"")
    }
}
