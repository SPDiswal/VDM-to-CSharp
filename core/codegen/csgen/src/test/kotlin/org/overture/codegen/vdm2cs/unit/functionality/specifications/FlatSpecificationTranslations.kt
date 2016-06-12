package org.overture.codegen.vdm2cs.unit.functionality.specifications

import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.specifications.Specifications
import org.overture.codegen.vdm2cs.unit.functionality.SpecificationTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class FlatSpecificationTranslations : SpecificationTranslationRuleSpek(Specifications)
{
    init
    {
        "An empty flat specification" describesThat
            flatSpecification(emptyList()) becomes
            publicStaticPartialClass("Global") { }

        "A flat specification with two functions" describesThat
            flatSpecification(listOf(
                functionDeclaration(name = "BoolFunc", resultType = boolType,
                                    parameters = emptyList(),
                                    body = true.toLiteral),

                functionDeclaration(name = "StringFunc", resultType = seqOf(charType),
                                    parameters = listOf(parameter("n", intType)),
                                    body = "Hello, World!".toLiteral)
            )) becomes
            publicStaticPartialClass("Global")
            {
                +purePublicStaticMethod(name = "BoolFunc", returnType = "bool",
                                        parameters = emptyList())
                {
                    +expressionBody("true")
                }

                +purePublicStaticMethod(name = "StringFunc", returnType = "string",
                                        parameters = listOf("n" to "int"))
                {
                    +ensures("${result("string")} != null")
                    +returns("\"Hello, World!\"")
                }
            }

        "A flat specification with a type and an operation" describesThat
            flatSpecification(listOf(
                namedTypeDeclaration(name = "BoolAlias", type = boolType),

                operationDeclaration(name = "StringOp", resultType = seqOf(charType),
                                     parameters = listOf(parameter("n", intType)),
                                     body = returnStatement("Hello, World!".toLiteral))
            )) becomes
            publicStaticPartialClass("Global")
            {
                +publicSealedDataClass(name = "BoolAlias", fields = listOf(
                    DefaultFieldEntry("Value", "bool")
                ))
                {
                    +implicitCastOperatorViaInstantiation(fromType = "bool", toType = "BoolAlias")
                    +implicitCastOperatorViaValueProperty(fromType = "BoolAlias", toType = "bool")
                }

                +publicStaticMethod(name = "StringOp", returnType = "string",
                                    parameters = listOf("n" to "int"))
                {
                    +ensures("${result("string")} != null")
                    +returns("\"Hello, World!\"")
                }
            }

        "A flat specification with a function and its precondition" describesThat
            flatSpecification(listOf(
                functionDeclaration(name = "BoolFunc", resultType = boolType,
                                    parameters = listOf(parameter("n", intType)),
                                    body = isNotYetSpecifiedExpression,
                                    precondition = true.toLiteral)
            )) becomes
            publicStaticPartialClass("Global")
            {
                +purePublicStaticMethod(name = "BoolFunc", returnType = "bool",
                                        parameters = listOf("n" to "int"))
                {
                    +requires("PreBoolFunc(n)")
                    +throwsNotImplementedException()
                }

                +purePublicStaticMethod(name = "PreBoolFunc", returnType = "bool",
                                        parameters = listOf("n" to "int"))
                {
                    +expressionBody("true")
                }
            }

        "A flat specification with a function and its postcondition" describesThat
            flatSpecification(listOf(
                functionDeclaration(name = "BoolFunc", resultType = boolType,
                                    parameters = listOf(parameter("n", intType)),
                                    body = isNotYetSpecifiedExpression,
                                    postcondition = true.toLiteral)
            )) becomes
            publicStaticPartialClass("Global")
            {
                +purePublicStaticMethod(name = "BoolFunc", returnType = "bool",
                                        parameters = listOf("n" to "int"))
                {
                    +ensures("PostBoolFunc(n, ${result("bool")})")
                    +throwsNotImplementedException()
                }

                +purePublicStaticMethod(name = "PostBoolFunc", returnType = "bool",
                                        parameters = listOf("n" to "int",
                                                            "result" to "bool"))
                {
                    +expressionBody("true")
                }
            }

        "A flat specification with an operation and its precondition" describesThat
            flatSpecification(listOf(
                operationDeclaration(name = "StringOp", resultType = seqOf(charType),
                                     parameters = listOf(parameter("n", intType)),
                                     body = isNotYetSpecifiedStatement,
                                     precondition = true.toLiteral)
            )) becomes
            publicStaticPartialClass("Global")
            {
                +publicStaticMethod(name = "StringOp", returnType = "string",
                                    parameters = listOf("n" to "int"))
                {
                    +requires("PreStringOp(n)")
                    +ensures("${result("string")} != null")
                    +throwsNotImplementedException()
                }

                +purePublicStaticMethod(name = "PreStringOp", returnType = "bool",
                                        parameters = listOf("n" to "int"))
                {
                    +expressionBody("true")
                }
            }

        "A flat specification with an operation and its postcondition" describesThat
            flatSpecification(listOf(
                operationDeclaration(name = "StringOp", resultType = seqOf(charType),
                                     parameters = listOf(parameter("n", intType)),
                                     body = isNotYetSpecifiedStatement,
                                     postcondition = true.toLiteral)
            )) becomes
            publicStaticPartialClass("Global")
            {
                +publicStaticMethod(name = "StringOp", returnType = "string",
                                    parameters = listOf("n" to "int"))
                {
                    +ensures("${result("string")} != null")
                    +ensures("PostStringOp(n, ${result("string")})")
                    +throwsNotImplementedException()
                }

                +purePublicStaticMethod(name = "PostStringOp", returnType = "bool",
                                        parameters = listOf("n" to "int", "result" to "string"))
                {
                    +requires("result != null")
                    +returns("true")
                }
            }

        "A flat specification with a state" describesThat
            flatSpecification(listOf(
                stateDeclaration(name = "St", fields = listOf(field("x", natType),
                                                              field("y", seqOf(charType))))
            )) becomes
            publicStaticPartialClass("Global")
            {
                +publicSealedDataClass(name = "St", fields = listOf(
                    DefaultFieldEntry("X", "int", isMutable = true),
                    StringFieldEntry("Y", "string", isMutable = true)
                ), invariants = listOf(
                    "X >= 0",
                    "Y != null"
                ))

                +privateStaticAutoProperty(name = "State", type = "St")
            }

        "A flat specification with a state and an operation with pre- and postconditions" describesThat
            flatSpecification(listOf(
                stateDeclaration(name = "St", fields = listOf(field("x", natType),
                                                              field("y", seqOf(charType)))),
                operationDeclaration(name = "AdvancedStringOp", resultType = seqOf(charType),
                                     parameters = listOf(parameter("n", intType)),
                                     body = isNotYetSpecifiedStatement,
                                     precondition = true.toLiteral,
                                     postcondition = false.toLiteral,
                                     state = "St" to recordType(name = "St"))
            )) becomes
            publicStaticPartialClass("Global")
            {
                +publicSealedDataClass(name = "St", fields = listOf(
                    DefaultFieldEntry("X", "int", isMutable = true),
                    StringFieldEntry("Y", "string", isMutable = true)
                ), invariants = listOf(
                    "X >= 0",
                    "Y != null"
                ))

                +privateStaticAutoProperty(name = "State", type = "St")

                +publicStaticMethod(name = "AdvancedStringOp", returnType = "string",
                                    parameters = listOf("n" to "int"))
                {
                    +requires("PreAdvancedStringOp(n, State)")
                    +ensures("${result("string")} != null")
                    +ensures("PostAdvancedStringOp(n, ${result("string")}, ${oldValue("State")}, State)")
                    +throwsNotImplementedException()
                }

                +purePublicStaticMethod(name = "PreAdvancedStringOp", returnType = "bool",
                                        parameters = listOf("n" to "int",
                                                            "st" to "St"))
                {
                    +requires("st != null")
                    +returns("true")
                }

                +purePublicStaticMethod(name = "PostAdvancedStringOp", returnType = "bool",
                                        parameters = listOf("n" to "int",
                                                            "result" to "string",
                                                            "oldSt" to "St",
                                                            "st" to "St"))
                {
                    +requires("result != null")
                    +requires("oldSt != null")
                    +requires("st != null")
                    +returns("false")
                }
            }

        "A flat specification with two values and a function" describesThat
            flatSpecification(listOf(
                valueDeclaration(name = "AlphaValue", type = boolType, value = true.toLiteral),
                valueDeclaration(name = "BravoValue", type = natType, value = 3.toLiteral),
                functionDeclaration(name = "CharlieFunc", resultType = natType,
                                    parameters = emptyList(),
                                    body = 4.toLiteral)
            )) becomes
            publicStaticPartialClass("Global")
            {
                +publicStaticInitialisedReadonlyAutoProperty(name = "AlphaValue", type = "bool", initialiser = "true")
                +publicStaticInitialisedReadonlyAutoProperty(name = "BravoValue", type = "int", initialiser = "3")

                +purePublicStaticMethod(name = "CharlieFunc", returnType = "int",
                                        parameters = emptyList())
                {
                    +ensures("${result("int")} >= 0")
                    +returns("4")
                }
            }
    }
}
