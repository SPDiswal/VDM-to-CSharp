package org.overture.codegen.vdm2cs.unit.functionality.states

import org.overture.codegen.ir.declarations.AStateDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsPropertyDeclaration
import org.overture.codegen.vdm2cs.parser.builders.privateStaticAutoProperty
import org.overture.codegen.vdm2cs.translations.functionality.states.States
import org.overture.codegen.vdm2cs.unit.functionality.DefinitionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class StateTranslations : DefinitionTranslationRuleSpek<AStateDeclIR, CsPropertyDeclaration>(States)
{
    init
    {
        "state $nextPlaceholder of end" describesThat
            stateDeclaration(name = placeholder, fields = emptyList()) becomes
            privateStaticAutoProperty(name = "State", type = placeholder)

        "state $nextLowerCasePlaceholder of x: int y: int end" describesThat
            stateDeclaration(name = lowerCasePlaceholder,
                             fields = listOf(field("x", intType),
                                             field("y", intType))) becomes
            privateStaticAutoProperty(name = "State", type = placeholder)

        "state $nextPlaceholder of text: seq of char init text = \"Hello, World!\" end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("text", seqOf(charType))),
                             initialiser = mkRecord(typeName(name = placeholder), listOf(
                                 "Hello, World!".toLiteral
                             ))) becomes
            privateStaticAutoProperty(name = "State", type = placeholder,
                                      initialiser = "new $placeholder(\"Hello, World!\")")

        "state $nextPlaceholder of a: nat b: nat init a = 1, b = 2 end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("x", natType),
                                             field("y", natType)),
                             initialiser = mkRecord(typeName(name = placeholder), listOf(
                                 1.toLiteral, 2.toLiteral
                             ))) becomes
            privateStaticAutoProperty(name = "State", type = placeholder,
                                      initialiser = "new $placeholder(1, 2)")
    }
}
