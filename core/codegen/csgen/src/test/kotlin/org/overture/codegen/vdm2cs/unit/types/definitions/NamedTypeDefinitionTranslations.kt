package org.overture.codegen.vdm2cs.unit.types.definitions

import org.overture.codegen.ir.declarations.ATypeDeclIR
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.types.definitions.NamedTypeDefinitions
import org.overture.codegen.vdm2cs.utilities.*

class NamedTypeDefinitionTranslations : TypeDefinitionTranslationRuleSpek<ATypeDeclIR>(NamedTypeDefinitions)
{
    init
    {
        "$nextPlaceholder = bool" describesThat
            namedTypeDeclaration(name = placeholder, type = boolType) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("Value", "bool")
            ))
            {
                +implicitCastOperatorViaInstantiation(fromType = "bool", toType = placeholder)
                +implicitCastOperatorViaValueProperty(fromType = placeholder, toType = "bool")
            }

        "$nextLowerCasePlaceholder = nat" describesThat
            namedTypeDeclaration(name = lowerCasePlaceholder, type = natType) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("Value", "int")
            ), invariants = listOf(
                "Value >= 0"
            ))
            {
                +implicitCastOperatorViaInstantiation(fromType = "int", toType = placeholder)
                +implicitCastOperatorViaValueProperty(fromType = placeholder, toType = "int")
            }

        "$nextPlaceholder = seq of char" describesThat
            namedTypeDeclaration(name = placeholder, type = seqOf(charType)) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                StringFieldEntry("Value", "string")
            ), invariants = listOf(
                "Value != null"
            ))
            {
                +implicitCastOperatorViaInstantiation(fromType = "string", toType = placeholder)
                +implicitCastOperatorViaValueProperty(fromType = placeholder, toType = "string")
            }

        "$nextPlaceholder = bool inv b = true" describesThat
            namedTypeDeclaration(name = placeholder, type = boolType,
                                 invariant = "b" to
                                     (identifier("b", boolType) isEqualTo true.toLiteral)) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("Value", "bool")
            ),invariants = listOf(
                "Inv$placeholder(Value)"
            ))
            {
                +purePublicStaticMethod(name = "Inv$placeholder", returnType = "bool",
                                        parameters = listOf("b" to "bool"))
                {
                    +expressionBody("b == true")
                }

                +implicitCastOperatorViaInstantiation(fromType = "bool", toType = placeholder)
                +implicitCastOperatorViaValueProperty(fromType = placeholder, toType = "bool")
            }

        "$nextPlaceholder = nat1 inv n > 4" describesThat
            namedTypeDeclaration(name = placeholder, type = nat1Type,
                                 invariant = "n" to
                                     (identifier("n", nat1Type) isGreaterThan 4.toLiteral)) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("Value", "int")
            ),invariants = listOf(
                "Value > 0",
                "Inv$placeholder(Value)"
            ))
            {
                +purePublicStaticMethod(name = "Inv$placeholder", returnType = "bool",
                                        parameters = listOf("n" to "int"))
                {
                    +requires("n > 0")
                    +returns("n > 4")
                }

                +implicitCastOperatorViaInstantiation(fromType = "int", toType = placeholder)
                +implicitCastOperatorViaValueProperty(fromType = placeholder, toType = "int")
            }
    }
}
