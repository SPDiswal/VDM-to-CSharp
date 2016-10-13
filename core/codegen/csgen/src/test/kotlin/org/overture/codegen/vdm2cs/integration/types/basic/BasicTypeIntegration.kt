package org.overture.codegen.vdm2cs.integration.types.basic

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek

class BasicTypeIntegration : TranscompilerIntegrationSpek()
//{
//    init
//    {
//        vdm {
//            function(name = nextPlaceholder, returnType = "bool",
//                     parameters = listOf("x" to "bool"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "bool",
//                                   parameters = listOf("x" to "bool"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "bool",
//                                                       "r" to "bool"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "char",
//                     parameters = listOf("x" to "char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "char",
//                                   parameters = listOf("x" to "char"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "char",
//                                                       "r" to "char"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "int",
//                     parameters = listOf("x" to "int"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "int",
//                                   parameters = listOf("x" to "int"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "int",
//                                                       "r" to "int"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "nat",
//                     parameters = listOf("x" to "nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "int",
//                                   parameters = listOf("x" to "int"))
//            {
//                requires("x >= 0")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result >= 0")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "int",
//                                                       "r" to "int"))
//            {
//                requires("x >= 0")
//                requires("r >= 0")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "nat1",
//                     parameters = listOf("x" to "nat1"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "int",
//                                   parameters = listOf("x" to "int"))
//            {
//                requires("x > 0")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result > 0")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "int",
//                                                       "r" to "int"))
//            {
//                requires("x > 0")
//                requires("r > 0")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "rat",
//                     parameters = listOf("x" to "rat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "decimal",
//                                   parameters = listOf("x" to "decimal"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "decimal",
//                                                       "r" to "decimal"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "real",
//                     parameters = listOf("x" to "real"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "decimal",
//                                   parameters = listOf("x" to "decimal"))
//            {
//                ensures("Post$placeholder(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "decimal",
//                                                       "r" to "decimal"))
//            {
//                expressionBody("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "token",
//                     parameters = listOf("x" to "token"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Token",
//                                   parameters = listOf("x" to "Token"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Token",
//                                                       "r" to "Token"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//    }
//}
