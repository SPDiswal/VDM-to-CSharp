package org.overture.codegen.vdm2cs.integration.types.compound

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek

final class CompositeTypeIntegration : TranscompilerIntegrationSpek()
//{
//    // TODO Composite types defined by 'compose ID of ... end' used directly without a type alias,
//    //      e.g. a map type definition: map int to compose ID of ... end is transcompiled to Dictionary<int, ID>.
//    // TODO Anonymous fields.
//    // TODO Equality abstraction fields.
//    // TODO Records in records.
//
//    init
//    {
//        vdm {
//            recordType("${nextPlaceholder}Record")
//            function(name = placeholder, returnType = "${placeholder}Record",
//                     parameters = listOf("x" to "${placeholder}Record"))
//        } becomes cs {
//            publicSealedDataClass(name = "${placeholder}Record")
//            {
//                emptyEquatableEqualsMethod()
//                objectEqualsMethod()
//            }
//
//            purePublicStaticMethod(name = placeholder, returnType = "${placeholder}Record",
//                                   parameters = listOf("x" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "${placeholder}Record",
//                                                       "r" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            recordType("${nextPlaceholder}Record")
//            {
//                field(name = "b", type = "bool")
//            }
//            function(name = placeholder, returnType = "${placeholder}Record",
//                     parameters = listOf("x" to "${placeholder}Record"))
//        } becomes cs {
//            publicSealedDataClass(name = "${placeholder}Record")
//            {
//                publicProperty(name = "B", type = "bool")
//                {
//                    publicAutoGetter()
//                    publicAutoSetter()
//                }
//
//                equatableEqualsMethod(fieldNames = listOf("B"))
//                objectEqualsMethod()
//            }
//
//            purePublicStaticMethod(name = placeholder, returnType = "${placeholder}Record",
//                                   parameters = listOf("x" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "${placeholder}Record",
//                                                       "r" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            recordType("${nextPlaceholder}Record")
//            {
//                field(name = "first", type = "nat1")
//                field(name = "second", type = "nat1")
//                inv(pattern = "c", invariant = "c.second > 10")
//            }
//            function(name = placeholder, returnType = "${placeholder}Record",
//                     parameters = listOf("x" to "${placeholder}Record"))
//        } becomes cs {
//            publicSealedDataClass(name = "${placeholder}Record")
//            {
//                publicProperty(name = "First", type = "int")
//                {
//                    publicAutoGetter()
//                    publicAutoSetter()
//                }
//
//                publicProperty(name = "Second", type = "int")
//                {
//                    publicAutoGetter()
//                    publicAutoSetter()
//                }
//
//                equatableEqualsMethod(fieldNames = listOf("First", "Second"))
//                objectEqualsMethod()
//
//                purePublicStaticMethod(name = "Inv${placeholder}Record", returnType = "bool",
//                                       parameters = listOf("c" to "${placeholder}Record"))
//                {
//                    requires("c != null")
//                    returns("c.second > 10")
//                }
//
//                contractInvariantMethod {
//                    invariant("Inv${placeholder}Record(this)")
//                    invariant("First > 0")
//                    invariant("Second > 0")
//                }
//            }
//
//            purePublicStaticMethod(name = placeholder, returnType = "${placeholder}Record",
//                                   parameters = listOf("x" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "${placeholder}Record",
//                                                       "r" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            recordType("${nextPlaceholder}Record")
//            {
//                field(name = "id", type = "token")
//                field(name = "number", type = "int")
//                field(name = "text", type = "seq of char")
//            }
//            function(name = placeholder, returnType = "${placeholder}Record",
//                     parameters = listOf("x" to "${placeholder}Record"))
//        } becomes cs {
//            publicSealedDataClass(name = "${placeholder}Record")
//            {
//                publicProperty(name = "Id", type = "Token")
//                {
//                    publicAutoGetter()
//                    publicAutoSetter()
//                }
//
//                publicProperty(name = "Number", type = "int")
//                {
//                    publicAutoGetter()
//                    publicAutoSetter()
//                }
//
//                publicProperty(name = "Text", type = "string")
//                {
//                    publicAutoGetter()
//                    publicAutoSetter()
//                }
//
//                equatableEqualsMethod(fieldNames = listOf("Id", "Number", "Text"))
//                objectEqualsMethod()
//
//                contractInvariantMethod {
//                    invariant("Id != null")
//                    invariant("Text != null")
//                }
//            }
//
//            purePublicStaticMethod(name = placeholder, returnType = "${placeholder}Record",
//                                   parameters = listOf("x" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "${placeholder}Record",
//                                                       "r" to "${placeholder}Record"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//    }
//}
