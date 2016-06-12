package org.overture.codegen.vdm2cs.unit.functionality.operations

import org.overture.codegen.ir.declarations.AMethodDeclIR
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.operations.Operations
import org.overture.codegen.vdm2cs.unit.functionality.FunctionalityTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ImplicitOperationsTranslations : FunctionalityTranslationRuleSpek<AMethodDeclIR>(Operations)
{
    init
    {
        "$nextPlaceholder: () r: bool" describesThat
            operationDeclaration(name = placeholder, resultName = "r", resultType = boolType,
                                 parameters = emptyList(),
                                 postcondition = true.toLiteral) becomes
            publicStaticMethod(name = placeholder, returnType = "bool",
                               parameters = emptyList())
            {
                +ensures("Post$placeholder(${result("bool")})")
                +throwsNotImplementedException()
            }

        "pure $nextLowerCasePlaceholder: (a: int) b: int" describesThat
            operationDeclaration(name = lowerCasePlaceholder, resultName = "b", resultType = intType,
                                 parameters = listOf(parameter("a", intType)),
                                 isPure = true,
                                 precondition = false.toLiteral,
                                 postcondition = (1.toLiteral plus 1.toLiteral) isEqualTo 2.toLiteral) becomes
            purePublicStaticMethod(name = placeholder, returnType = "int",
                                   parameters = listOf("a" to "int"))
            {
                +requires("Pre$placeholder(a)")
                +ensures("Post$placeholder(a, ${result("int")})")
                +throwsNotImplementedException()
            }

        "$nextPlaceholder: (Numerator: int, Denominator: int) Quotient: rat" describesThat
            operationDeclaration(name = placeholder, resultName = "Quotient", resultType = ratType,
                                 parameters = listOf(parameter("Numerator", intType),
                                                     parameter("Denominator", intType)),
                                 precondition = (1.toLiteral plus 2.toLiteral) isEqualTo 3.toLiteral,
                                 postcondition = true.toLiteral) becomes
            publicStaticMethod(name = placeholder, returnType = "decimal",
                               parameters = listOf("numerator" to "int",
                                                   "denominator" to "int"))
            {
                +requires("Pre$placeholder(numerator, denominator)")
                +ensures("Post$placeholder(numerator, denominator, ${result("decimal")})")
                +throwsNotImplementedException()
            }
    }
}
