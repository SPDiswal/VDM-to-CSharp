package org.overture.codegen.vdm2cs.unit.functionality.states

import org.overture.codegen.ir.declarations.AStateDeclIR
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.states.StateTypeDefinitions
import org.overture.codegen.vdm2cs.unit.types.definitions.TypeDefinitionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class StateTypeDefinitionTranslations : TypeDefinitionTranslationRuleSpek<AStateDeclIR>(StateTypeDefinitions)
{
    init
    {
        "state $nextPlaceholder of end" describesThat
            stateDeclaration(name = placeholder,
                             fields = emptyList()) becomes
            publicSealedDataClass(name = placeholder, fields = emptyList())

        "state $nextLowerCasePlaceholder of first: int end" describesThat
            stateDeclaration(name = lowerCasePlaceholder,
                             fields = listOf(field("first", intType))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("First", "int", isMutable = true)
            ))

        "state $nextPlaceholder of text: seq of char numberSequence: seq of nat end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("text", seqOf(charType)),
                                             field("numberSequence", seqOf(natType)))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                StringFieldEntry("Text", "string", isMutable = true),
                SequenceFieldEntry("NumberSequence", "List<int>", isMutable = true)
            ), invariants = listOf(
                "Text != null",
                "NumberSequence != null && ${forAll("NumberSequence", "_ >= 0")}"
            ))

        "state $nextPlaceholder of pair: (nat, nat) numberSet: set of nat end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("pair", tupleOf(natType, natType)),
                                             field("numberSet", setOf(natType)))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                ObjectFieldEntry("Pair", "Tuple<int, int>", isMutable = true),
                SetFieldEntry("NumberSet", "HashSet<int>", isMutable = true)
            ), invariants = listOf(
                "Pair != null && Pair.Item1 >= 0 && Pair.Item2 >= 0",
                "NumberSet != null && ${forAll("NumberSet", "_ >= 0")}"
            ))

        "state $nextPlaceholder of number: nat union: int | bool numberMapping: inmap nat to nat end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("number", natType),
                                             field("union", unionOf(intType, boolType)),
                                             field("numberMapping", inmap(natType to natType)))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("Number", "int", isMutable = true),
                UnionFieldEntry("Union", "object", isMutable = true),
                MappingFieldEntry("NumberMapping", "Dictionary<int, int>", isMutable = true)
            ), invariants = listOf(
                "Number >= 0",
                "(Union is int || Union is bool)",
                "NumberMapping != null && NumberMapping.IsInjective() && " +
                "${forAll("NumberMapping.Keys", "_ >= 0")} && ${forAll("NumberMapping.Values", "_ >= 0")}"
            ))

        "state $nextPlaceholder of a: int b: int inv a > b end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("a", intType), field("b", intType)),
                             invariant = "s" to
                                 ((identifier("s", recordType(name = placeholder)) dot identifier("a", intType))
                                     isGreaterThan
                                     (identifier("s", recordType(name = placeholder)) dot
                                         identifier("b", intType)))) becomes
            publicSealedDataClass(name = placeholder, fields = listOf(
                DefaultFieldEntry("A", "int", isMutable = true),
                DefaultFieldEntry("B", "int", isMutable = true)
            ), invariants = listOf(
                "Inv$placeholder(this)"
            ))
            {
                +purePublicStaticMethod(name = "Inv$placeholder", returnType = "bool",
                                        parameters = listOf("s" to placeholder))
                {
                    +requires("s != null")
                    +returns("s.A > s.B")
                }
            }

        "state $nextPlaceholder of x: nat y: seq of char inv x = 4 end" describesThat
            stateDeclaration(name = placeholder,
                             fields = listOf(field("x", natType), field("y", seqOf(charType))),
                             invariant = "st" to
                                 ((identifier("st", recordType(name = placeholder)) dot identifier("x", natType))
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
                                        parameters = listOf("st" to placeholder))
                {
                    +requires("st != null")
                    +returns("st.X == 4")
                }
            }
    }
}
