package org.overture.codegen.vdm2cs.unit.functionality.specifications

import org.overture.codegen.vdm2cs.parser.builders.publicStaticClass
import org.overture.codegen.vdm2cs.translations.functionality.specifications.Specifications
import org.overture.codegen.vdm2cs.unit.functionality.SpecificationTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.module

class ModuleTranslations : SpecificationTranslationRuleSpek(Specifications)
{
    init
    {
        "An empty module" describesThat
            module(name = nextPlaceholder,
                   definitions = emptyList()) becomes
            publicStaticClass(name = placeholder) { }

//        "A module with two exported functions" describesThat
//            flatSpecification(listOf(
//                functionDeclaration(name = "BoolFunc", resultType = boolType,
//                                    parameters = emptyList(),
//                                    body = true.toLiteral),
//
//                functionDeclaration(name = "StringFunc", resultType = seqOf(charType),
//                                    parameters = listOf(parameter("n", intType)),
//                                    body = "Hello, World!".toLiteral)
//            )) becomes
//            publicStaticClass {
//                purePublicStaticMethod(name = "BoolFunc", returnType = "bool",
//                                       parameters = emptyList())
//                {
//                    expressionBody("true")
//                }
//
//                purePublicStaticMethod(name = "StringFunc", returnType = "string",
//                                       parameters = listOf("n" to "int"))
//                {
//                    expressionBody("\"Hello, World!\"")
//                }
//            }
//
//        "A module with an exported type and an exported operation" describesThat
//            flatSpecification(listOf(
//                namedTypeDeclaration(name = "BoolAlias", type = boolType),
//
//                operationDeclaration(name = "StringOp", resultType = seqOf(charType),
//                                     parameters = listOf(parameter("n", intType)),
//                                     body = returnStatement("Hello, World!".toLiteral))
//            )) becomes
//            publicStaticClass {
//                publicSealedDataClass(name = "BoolAlias")
//                {
//                    publicProperty(name = "Value", type = "bool")
//                    {
//                        publicAutoGetter()
//                        publicAutoSetter()
//                    }
//
//                    objectEqualsMethod()
//                    equatableEqualsMethod(fieldNames = listOf("Value"))
//                }
//
//                publicStaticMethod(name = "StringOp", returnType = "string",
//                                   parameters = listOf("n" to "int"))
//                {
//                    expressionBody("\"Hello, World!\"")
//                }
//            }
    }
}
