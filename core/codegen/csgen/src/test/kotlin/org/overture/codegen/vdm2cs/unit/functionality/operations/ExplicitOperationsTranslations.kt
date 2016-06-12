package org.overture.codegen.vdm2cs.unit.functionality.operations

import org.overture.codegen.ir.declarations.AMethodDeclIR
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.operations.Operations
import org.overture.codegen.vdm2cs.unit.functionality.FunctionalityTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ExplicitOperationsTranslations : FunctionalityTranslationRuleSpek<AMethodDeclIR>(Operations)
{
    init
    {
        "$nextPlaceholder: () ==> ()" describesThat
            operationDeclaration(name = placeholder, resultType = voidType,
                                 parameters = emptyList(),
                                 body = skipStatement) becomes
            publicStaticMethod(name = placeholder, returnType = "void",
                               parameters = emptyList())

        "pure $nextLowerCasePlaceholder: () ==> bool" describesThat
            operationDeclaration(name = lowerCasePlaceholder, resultType = boolType,
                                 parameters = emptyList(),
                                 isPure = true,
                                 body = returnStatement(true.toLiteral)) becomes
            purePublicStaticMethod(name = placeholder, returnType = "bool",
                                   parameters = emptyList())
            {
                +expressionBody("true")
            }

        "$nextPlaceholder: () ==> int" describesThat
            operationDeclaration(name = placeholder, resultType = intType,
                                 parameters = emptyList(),
                                 body = returnStatement(1.toLiteral)) becomes
            publicStaticMethod(name = placeholder, returnType = "int",
                               parameters = emptyList())
            {
                +expressionBody("1")
            }

        "pure $nextPlaceholder: n: int ==> seq of char" describesThat
            operationDeclaration(name = placeholder, resultType = seqOf(charType),
                                 parameters = listOf(parameter("n", intType)),
                                 isPure = true,
                                 body = returnStatement("Hello, World!".toLiteral)) becomes
            purePublicStaticMethod(name = placeholder, returnType = "string",
                                   parameters = listOf("n" to "int"))
            {
                +ensures("${result("string")} != null")
                +returns("\"Hello, World!\"")
            }

        "$nextPlaceholder: Numerator: int * Denominator: int ==> rat" describesThat
            operationDeclaration(name = placeholder, resultType = ratType,
                                 parameters = listOf(parameter("Numerator", intType),
                                                     parameter("Denominator", intType)),
                                 precondition = identifier("Denominator", intType) isNotEqualTo 0.toLiteral,
                                 postcondition = true.toLiteral,
                                 body = returnStatement(identifier("Numerator", intType) over
                                                            identifier("Denominator", intType))) becomes
            publicStaticMethod(name = placeholder, returnType = "decimal",
                               parameters = listOf("numerator" to "int",
                                                   "denominator" to "int"))
            {
                +requires("Pre$placeholder(numerator, denominator)")
                +ensures("Post$placeholder(numerator, denominator, ${result("decimal")})")
                +returns("numerator / denominator")
            }

        "$nextPlaceholder: b: bool ==> ()" describesThat
            operationDeclaration(name = placeholder, resultType = voidType,
                                 parameters = listOf(parameter("b", boolType)),
                                 body = ifStatement(ifCondition = identifier("b", boolType),
                                                    thenStatement = skipStatement)) becomes
            publicStaticMethod(name = placeholder, returnType = "void",
                               parameters = listOf("b" to "bool"))
            {
                +ifThen(ifCondition = "b", thenStatement = "{ }")
            }

        "$nextLowerCasePlaceholder: Factors: seq of int ==> int * seq of char" describesThat
            operationDeclaration(name = lowerCasePlaceholder, resultType = tupleOf(intType, seqOf(charType)),
                                 parameters = listOf(parameter("Factors", seqOf(intType))),
                                 postcondition = false.toLiteral,
                                 body = isNotYetSpecifiedStatement) becomes
            publicStaticMethod(name = placeholder, returnType = "Tuple<int, string>",
                               parameters = listOf("factors" to "List<int>"))
            {
                +requires("factors != null")
                +ensures("${result("Tuple<int, string>")} != null && ${result("Tuple<int, string>")}.Item2 != null")
                +ensures("Post$placeholder(factors, ${result("Tuple<int, string>")})")
                +throwsNotImplementedException()
            }

        "$nextPlaceholder: x: nat * y: nat1 ==> seq of char" describesThat
            operationDeclaration(name = placeholder, resultType = seqOf(charType),
                                 parameters = listOf(parameter("x", natType),
                                                     parameter("y", nat1Type)),
                                 body = returnStatement("To be or not to be".toLiteral)) becomes
            publicStaticMethod(name = placeholder, returnType = "string",
                               parameters = listOf("x" to "int",
                                                   "y" to "int"))
            {
                +requires("x >= 0")
                +requires("y > 0")
                +ensures("${result("string")} != null")
                +returns("\"To be or not to be\"")
            }

        "$nextPlaceholder: a: bool * b: token * c: seq of nat1 ==> nat1" describesThat
            operationDeclaration(name = placeholder, resultType = nat1Type,
                                 parameters = listOf(parameter("a", boolType),
                                                     parameter("b", tokenType),
                                                     parameter("c", seqOf(nat1Type))),
                                 body = returnStatement(42.toLiteral)) becomes
            publicStaticMethod(name = placeholder, returnType = "int",
                               parameters = listOf("a" to "bool",
                                                   "b" to "Token",
                                                   "c" to "List<int>"))
            {
                +requires("b != null")
                +requires("c != null && ${forAll("c", "_ > 0")}")
                +ensures("${result("int")} > 0")
                +returns("42")
            }

        "$nextPlaceholder: () ==> ()" describesThat
            operationDeclaration(name = placeholder, resultType = voidType,
                                 parameters = emptyList(),
                                 postcondition = true.toLiteral,
                                 body = skipStatement,
                                 state = "State" to intType) becomes
            publicStaticMethod(name = placeholder, returnType = "void",
                               parameters = emptyList())
            {
                +ensures("Post$placeholder(${oldValue("State")}, State)")
            }

        "$nextPlaceholder: () ==> int" describesThat
            operationDeclaration(name = placeholder, resultType = intType,
                                 parameters = emptyList(),
                                 precondition = true.toLiteral,
                                 postcondition = false.toLiteral,
                                 body = returnStatement(42.toLiteral),
                                 state = "st" to intType) becomes
            publicStaticMethod(name = placeholder, returnType = "int",
                               parameters = emptyList())
            {
                +requires("Pre$placeholder(State)")
                +ensures("Post$placeholder(${result("int")}, ${oldValue("State")}, State)")
                +returns("42")
            }

        "$nextPlaceholder[@TFirst]: () ==> @TFirst" describesThat
            operationDeclaration(name = placeholder, resultType = templateType("TFirst"),
                                 typeParameters = listOf("TFirst"),
                                 parameters = emptyList(),
                                 body = isNotYetSpecifiedStatement) becomes
            publicStaticMethod(name = placeholder, returnType = "TFirst",
                               typeParameters = listOf("TFirst"),
                               parameters = emptyList())
            {
                +throwsNotImplementedException()
            }

        "pure $nextPlaceholder[@alpha, @bravo]: a: @alpha ==> @bravo" describesThat
            operationDeclaration(name = placeholder, resultType = templateType("bravo"),
                                 typeParameters = listOf("alpha", "bravo"),
                                 parameters = listOf(parameter("a", templateType("alpha"))),
                                 isPure = true,
                                 body = isNotYetSpecifiedStatement) becomes
            purePublicStaticMethod(name = placeholder, returnType = "TBravo",
                                   typeParameters = listOf("TAlpha", "TBravo"),
                                   parameters = listOf("a" to "TAlpha"))
            {
                +throwsNotImplementedException()
            }
    }
}
