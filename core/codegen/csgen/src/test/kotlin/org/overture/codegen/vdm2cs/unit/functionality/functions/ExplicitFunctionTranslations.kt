package org.overture.codegen.vdm2cs.unit.functionality.functions

import org.overture.codegen.ir.declarations.AFuncDeclIR
import org.overture.codegen.vdm2cs.parser.builders.purePublicStaticMethod
import org.overture.codegen.vdm2cs.utilities.forAll
import org.overture.codegen.vdm2cs.translations.functionality.functions.Functions
import org.overture.codegen.vdm2cs.unit.functionality.FunctionalityTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ExplicitFunctionTranslations : FunctionalityTranslationRuleSpek<AFuncDeclIR>(Functions)
{
    init
    {
        "$nextPlaceholder: () -> bool" describesThat
            functionDeclaration(name = placeholder, resultType = boolType,
                                parameters = emptyList(),
                                body = true.toLiteral) becomes
            purePublicStaticMethod(name = placeholder, returnType = "bool",
                                   parameters = emptyList())
            {
                +expressionBody("true")
            }

        "$nextLowerCasePlaceholder: () -> int" describesThat
            functionDeclaration(name = lowerCasePlaceholder, resultType = intType,
                                parameters = emptyList(),
                                body = 1.toLiteral) becomes
            purePublicStaticMethod(name = placeholder, returnType = "int",
                                   parameters = emptyList())
            {
                +expressionBody("1")
            }

        "$nextPlaceholder: n: int -> seq of char" describesThat
            functionDeclaration(name = placeholder, resultType = seqOf(charType),
                                parameters = listOf(parameter("n", intType)),
                                body = "Hello, World!".toLiteral) becomes
            purePublicStaticMethod(name = placeholder, returnType = "string",
                                   parameters = listOf("n" to "int"))
            {
                +ensures("${result("string")} != null")
                +returns("\"Hello, World!\"")
            }

        "$nextLowerCasePlaceholder: Numerator: int * Denominator: int -> rat" describesThat
            functionDeclaration(name = lowerCasePlaceholder, resultType = ratType,
                                parameters = listOf(parameter("Numerator", intType),
                                                    parameter("Denominator", intType)),
                                precondition = identifier("Denominator", intType) isNotEqualTo 0.toLiteral,
                                postcondition = true.toLiteral,
                                body = identifier("Numerator", intType) over
                                    identifier("Denominator", intType)) becomes
            purePublicStaticMethod(name = placeholder, returnType = "decimal",
                                   parameters = listOf("numerator" to "int",
                                                       "denominator" to "int"))
            {
                +requires("Pre$placeholder(numerator, denominator)")
                +ensures("Post$placeholder(numerator, denominator, ${result("decimal")})")
                +returns("numerator / denominator")
            }

        "$nextPlaceholder: factors: seq of int -> int * seq of char" describesThat
            functionDeclaration(name = placeholder, resultType = tupleOf(intType, seqOf(charType)),
                                parameters = listOf(parameter("factors", seqOf(intType))),
                                precondition = true.toLiteral,
                                postcondition = false.toLiteral,
                                body = isNotYetSpecifiedExpression) becomes
            purePublicStaticMethod(name = placeholder, returnType = "Tuple<int, string>",
                                   parameters = listOf("factors" to "List<int>"))
            {
                +requires("factors != null")
                +requires("Pre$placeholder(factors)")
                +ensures("${result("Tuple<int, string>")} != null && ${result("Tuple<int, string>")}.Item2 != null")
                +ensures("Post$placeholder(factors, ${result("Tuple<int, string>")})")
                +throwsNotImplementedException()
            }

        "$nextPlaceholder: x: nat * y: nat1 -> seq of char" describesThat
            functionDeclaration(name = placeholder, resultType = seqOf(charType),
                                parameters = listOf(parameter("x", natType),
                                                    parameter("y", nat1Type)),
                                body = "To be or not to be".toLiteral) becomes
            purePublicStaticMethod(name = placeholder, returnType = "string",
                                   parameters = listOf("x" to "int",
                                                       "y" to "int"))
            {
                +requires("x >= 0")
                +requires("y > 0")
                +ensures("${result("string")} != null")
                +returns("\"To be or not to be\"")
            }

        "$nextPlaceholder: a: bool * b: token * c: seq of nat1 -> nat1" describesThat
            functionDeclaration(name = placeholder, resultType = nat1Type,
                                parameters = listOf(parameter("a", boolType),
                                                    parameter("b", tokenType),
                                                    parameter("c", seqOf(nat1Type))),
                                body = 42.toLiteral) becomes
            purePublicStaticMethod(name = placeholder, returnType = "int",
                                   parameters = listOf("a" to "bool",
                                                       "b" to "Token",
                                                       "c" to "List<int>"))
            {
                +requires("b != null")
                +requires("c != null && ${forAll("c", "_ > 0")}")
                +ensures("${result("int")} > 0")
                +returns("42")
            }

        "$nextPlaceholder[@TFirst]: () -> @TFirst" describesThat
            functionDeclaration(name = placeholder, resultType = templateType("TFirst"),
                                typeParameters = listOf("TFirst"),
                                parameters = emptyList(),
                                body = isNotYetSpecifiedExpression) becomes
            purePublicStaticMethod(name = placeholder, returnType = "TFirst",
                                   typeParameters = listOf("TFirst"),
                                   parameters = emptyList())
            {
                +throwsNotImplementedException()
            }

        "$nextPlaceholder[@alpha, @bravo]: a: @alpha -> @bravo" describesThat
            functionDeclaration(name = placeholder, resultType = templateType("bravo"),
                                typeParameters = listOf("alpha", "bravo"),
                                parameters = listOf(parameter("a", templateType("alpha"))),
                                body = isNotYetSpecifiedExpression) becomes
            purePublicStaticMethod(name = placeholder, returnType = "TBravo",
                                   typeParameters = listOf("TAlpha", "TBravo"),
                                   parameters = listOf("a" to "TAlpha"))
            {
                +throwsNotImplementedException()
            }
    }
}
