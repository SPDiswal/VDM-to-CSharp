package org.overture.codegen.vdm2cs.integration.types.compound

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek
import org.overture.codegen.vdm2cs.utilities.forAll

class SetTypeIntegration : TranscompilerIntegrationSpek()
//{
//    init
//    {
//        vdm {
//            function(name = nextPlaceholder, returnType = "set of char",
//                     parameters = listOf("x" to "set of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "HashSet<char>",
//                                   parameters = listOf("x" to "HashSet<char>"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "HashSet<char>",
//                                                       "r" to "HashSet<char>"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "set of nat",
//                     parameters = listOf("x" to "set of nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "HashSet<int>",
//                                   parameters = listOf("x" to "HashSet<int>"))
//            {
//                requires("x != null")
//                requires("${forAll("x", "_ >= 0")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("${forAll("$result", "_ >= 0")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "HashSet<int>",
//                                                       "r" to "HashSet<int>"))
//            {
//                requires("x != null")
//                requires("${forAll("x", "_ >= 0")}")
//                requires("r != null")
//                requires("${forAll("r", "_ >= 0")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "set of set of char",
//                     parameters = listOf("x" to "set of set of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "HashSet<HashSet<char>>",
//                                   parameters = listOf("x" to "HashSet<HashSet<char>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x", "_ != null")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("${forAll("$result", "_ != null")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "HashSet<HashSet<char>>",
//                                                       "r" to "HashSet<HashSet<char>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x", "_ != null")}")
//                requires("r != null")
//                requires("${forAll("r", "_ != null")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "set of set of nat",
//                     parameters = listOf("x" to "set of set of nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "HashSet<HashSet<int>>",
//                                   parameters = listOf("x" to "HashSet<HashSet<int>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x", "_ != null")}")
//                requires("${forAll("x", "${forAll("_", "__ >= 0")}")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("${forAll("$result", "_ != null")}")
//                ensures("${forAll("$result", "${forAll("_", "__ >= 0")}")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "HashSet<HashSet<int>>",
//                                                       "r" to "HashSet<HashSet<int>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x", "_ != null")}")
//                requires("${forAll("x", "${forAll("_", "__ >= 0")}")}")
//                requires("r != null")
//                requires("${forAll("r", "_ != null")}")
//                requires("${forAll("r", "${forAll("_", "__ >= 0")}")}")
//                returns("true")
//            }
//        }
//    }
//}
