package org.overture.codegen.vdm2cs.integration.types.compound

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek
import org.overture.codegen.vdm2cs.utilities.forAll

class SeqTypeIntegration : TranscompilerIntegrationSpek()
//{
//    init
//    {
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq of char",
//                     parameters = listOf("x" to "seq of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "string",
//                                   parameters = listOf("x" to "string"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "string",
//                                                       "r" to "string"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq of nat",
//                     parameters = listOf("x" to "seq of nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<int>",
//                                   parameters = listOf("x" to "List<int>"))
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
//                                   parameters = listOf("x" to "List<int>",
//                                                       "r" to "List<int>"))
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
//            function(name = nextPlaceholder, returnType = "seq of seq of char",
//                     parameters = listOf("x" to "seq of seq of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<string>",
//                                   parameters = listOf("x" to "List<string>"))
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
//                                   parameters = listOf("x" to "List<string>",
//                                                       "r" to "List<string>"))
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
//            function(name = nextPlaceholder, returnType = "seq of seq of nat",
//                     parameters = listOf("x" to "seq of seq of nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<List<int>>",
//                                   parameters = listOf("x" to "List<List<int>>"))
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
//                                   parameters = listOf("x" to "List<List<int>>",
//                                                       "r" to "List<List<int>>"))
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
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq1 of char",
//                     parameters = listOf("x" to "seq1 of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "string",
//                                   parameters = listOf("x" to "string"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                ensures("Post$placeholder(x, $result)")
//                ensures("!$result.IsNullOrEmpty()")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "string",
//                                                       "r" to "string"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("!r.IsNullOrEmpty()")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq1 of nat",
//                     parameters = listOf("x" to "seq1 of nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<int>",
//                                   parameters = listOf("x" to "List<int>"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("${forAll("x", "_ >= 0")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("!$result.IsNullOrEmpty()")
//                ensures("${forAll("$result", "_ >= 0")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "List<int>",
//                                                       "r" to "List<int>"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("${forAll("x", "_ >= 0")}")
//                requires("!r.IsNullOrEmpty()")
//                requires("${forAll("r", "_ >= 0")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq1 of seq1 of char",
//                     parameters = listOf("x" to "seq1 of seq1 of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<string>",
//                                   parameters = listOf("x" to "List<string>"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("${forAll("x", "!_.IsNullOrEmpty()")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("!$result.IsNullOrEmpty()")
//                ensures("${forAll("$result", "!_.IsNullOrEmpty()")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "List<string>",
//                                                       "r" to "List<string>"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("${forAll("x", "!_.IsNullOrEmpty()")}")
//                requires("!r.IsNullOrEmpty()")
//                requires("${forAll("r", "!_.IsNullOrEmpty()")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq1 of seq1 of nat",
//                     parameters = listOf("x" to "seq1 of seq1 of nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "List<List<int>>",
//                                   parameters = listOf("x" to "List<List<int>>"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("${forAll("x", "!_.IsNullOrEmpty()")}")
//                requires("${forAll("x", "${forAll("_", "__ >= 0")}")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("!$result.IsNullOrEmpty()")
//                ensures("${forAll("$result", "!_.IsNullOrEmpty()")}")
//                ensures("${forAll("$result", "${forAll("_", "__ >= 0")}")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "List<List<int>>",
//                                                       "r" to "List<List<int>>"))
//            {
//                requires("!x.IsNullOrEmpty()")
//                requires("${forAll("x", "!_.IsNullOrEmpty()")}")
//                requires("${forAll("x", "${forAll("_", "__ >= 0")}")}")
//                requires("!r.IsNullOrEmpty()")
//                requires("${forAll("r", "!_.IsNullOrEmpty()")}")
//                requires("${forAll("r", "${forAll("_", "__ >= 0")}")}")
//                returns("true")
//            }
//        }
//    }
//}
