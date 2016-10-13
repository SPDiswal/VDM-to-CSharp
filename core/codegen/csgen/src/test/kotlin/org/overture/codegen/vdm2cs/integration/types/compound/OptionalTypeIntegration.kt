package org.overture.codegen.vdm2cs.integration.types.compound

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek
import org.overture.codegen.vdm2cs.utilities.forAll

class OptionalTypeIntegration : TranscompilerIntegrationSpek()
//{
//    init
//    {
//        vdm {
//            function(name = nextPlaceholder, returnType = "[bool]",
//                     parameters = listOf("x" to "[bool]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "bool?",
//                                   parameters = listOf("x" to "bool?"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "bool?",
//                                                       "r" to "bool?"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[char]",
//                     parameters = listOf("x" to "[char]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "char?",
//                                   parameters = listOf("x" to "char?"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "char?",
//                                                       "r" to "char?"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[int]",
//                     parameters = listOf("x" to "[int]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "int?",
//                                   parameters = listOf("x" to "int?"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "int?",
//                                                       "r" to "int?"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[nat]",
//                     parameters = listOf("x" to "[nat]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "int?",
//                                   parameters = listOf("x" to "int?"))
//            {
//                requires("x == null || x >= 0")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result == null || $result >= 0")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "int?",
//                                                       "r" to "int?"))
//            {
//                requires("x == null || x >= 0")
//                requires("r == null || r >= 0")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[nat1]",
//                     parameters = listOf("x" to "[nat1]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "int?",
//                                   parameters = listOf("x" to "int?"))
//            {
//                requires("x == null || x > 0")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result == null || $result > 0")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "int?",
//                                                       "r" to "int?"))
//            {
//                requires("x == null || x > 0")
//                requires("r == null || r > 0")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[rat]",
//                     parameters = listOf("x" to "[rat]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "decimal?",
//                                   parameters = listOf("x" to "decimal?"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "decimal?",
//                                                       "r" to "decimal?"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[real]",
//                     parameters = listOf("x" to "[real]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "decimal?",
//                                   parameters = listOf("x" to "decimal?"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "decimal?",
//                                                       "r" to "decimal?"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[token]",
//                     parameters = listOf("x" to "[token]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Token",
//                                   parameters = listOf("x" to "Token"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Token",
//                                                       "r" to "Token"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[seq of char]",
//                     parameters = listOf("x" to "[seq of char]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "string",
//                                   parameters = listOf("x" to "string"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "string",
//                                                       "r" to "string"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[seq1 of char]",
//                     parameters = listOf("x" to "[seq1 of char]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "string",
//                                   parameters = listOf("x" to "string"))
//            {
//                requires("x == null || !x.IsEmpty()")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result == null || !$result.IsEmpty()")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "string",
//                                                       "r" to "string"))
//            {
//                requires("x == null || !x.IsEmpty()")
//                requires("r == null || !r.IsEmpty()")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "[seq of [nat]]",
//                     parameters = listOf("x" to "[seq of [nat]]"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<int?>",
//                                   parameters = listOf("x" to "List<int?>"))
//            {
//                requires("x == null || ${forAll("x", "_ == null || _ >= 0")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result == null || ${forAll("$result", "_ == null || _ >= 0")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "List<int?>",
//                                                       "r" to "List<int?>"))
//            {
//                requires("x == null || ${forAll("x", "_ == null || _ >= 0")}")
//                requires("r == null || ${forAll("r", "_ == null || _ >= 0")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            recordType("${nextPlaceholder}Record")
//            function(name = placeholder, returnType = "[${placeholder}Record]",
//                     parameters = listOf("x" to "[${placeholder}Record]"))
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
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "${placeholder}Record",
//                                                       "r" to "${placeholder}Record"))
//            {
//                expressionBody("true")
//            }
//        }
//    }
//}
