package org.overture.codegen.vdm2cs.unit.types.definitions

import org.overture.codegen.ir.declarations.ATypeDeclIR
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.types.definitions.CompositeTypeDefinitions
import org.overture.codegen.vdm2cs.utilities.*

class CompositeTypeDefinitionTranslations : TypeDefinitionTranslationRuleSpek<ATypeDeclIR>(CompositeTypeDefinitions)
{
    init
    {
        "$nextPlaceholder :: " describesThat
            recordTypeDeclaration(name = placeholder, fields = emptyList()) becomes
            publicSealedDataClass(name = placeholder, fields = emptyList())

        "$nextLowerCasePlaceholder :: first: int" describesThat
            recordTypeDeclaration(name = lowerCasePlaceholder,
                                  fields = listOf(field("first", intType))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("First", "int", isMutable = true)
            ))

        "$nextPlaceholder :: number: nat text: seq of char" describesThat
            recordTypeDeclaration(name = placeholder,
                                  fields = listOf(field("number", natType),
                                                  field("text", seqOf(charType)))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("Number", "int", isMutable = true),
                StringFieldEntry("Text", "string", isMutable = true)
            ), invariants = listOf(
                "Number >= 0",
                "Text != null"
            ))

        "$nextPlaceholder :: a: int b: int inv a > b" describesThat
            recordTypeDeclaration(name = placeholder,
                                  fields = listOf(field("a", intType),
                                                  field("b", intType)),
                                  invariant = "r" to
                                      ((identifier("r", recordType(name = placeholder)) dot identifier("a", intType))
                                          isGreaterThan
                                          (identifier("r", recordType(name = placeholder)) dot
                                              identifier("b", intType)))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("A", "int", isMutable = true),
                DefaultFieldEntry("B", "int", isMutable = true)
            ), invariants = listOf(
                "Inv$placeholder(this)"
            ))
            {
                +purePublicStaticMethod(name = "Inv$placeholder", returnType = "bool",
                                        parameters = listOf("r" to placeholder))
                {
                    +requires("r != null")
                    +returns("r.A > r.B")
                }
            }

        "$nextPlaceholder :: x: nat y: seq of char inv x = 4" describesThat
            recordTypeDeclaration(name = placeholder,
                                  fields = listOf(field("x", natType),
                                                  field("y", seqOf(charType))),
                                  invariant = "rec" to
                                      ((identifier("rec", recordType(name = placeholder)) dot identifier("x", natType))
                                          isEqualTo 4.toLiteral)) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("X", "int", isMutable = true),
                StringFieldEntry("Y", "string", isMutable = true)
            ), invariants = listOf(
                "X >= 0",
                "Y != null",
                "Inv$placeholder(this)"
            ))
            {
                +purePublicStaticMethod(name = "Inv$placeholder", returnType = "bool",
                                        parameters = listOf("rec" to placeholder))
                {
                    +requires("rec != null")
                    +returns("rec.X == 4")
                }
            }
    }
}
