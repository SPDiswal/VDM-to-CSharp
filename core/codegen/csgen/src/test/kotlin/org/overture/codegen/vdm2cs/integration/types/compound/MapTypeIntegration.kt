package org.overture.codegen.vdm2cs.integration.types.compound

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek
import org.overture.codegen.vdm2cs.utilities.forAll

class MapTypeIntegration : TranscompilerIntegrationSpek()
//{
//    init
//    {
//        vdm {
//            function(name = nextPlaceholder, returnType = "map bool to bool",
//                     parameters = listOf("x" to "map bool to bool"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<bool, bool>",
//                                   parameters = listOf("x" to "Dictionary<bool, bool>"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<bool, bool>",
//                                                       "r" to "Dictionary<bool, bool>"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "map nat to nat1",
//                     parameters = listOf("x" to "map nat to nat1"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<int, int>",
//                                   parameters = listOf("x" to "Dictionary<int, int>"))
//            {
//                requires("x != null")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ > 0")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("${forAll("$result.Keys", "_ >= 0")}")
//                ensures("${forAll("$result.Values", "_ > 0")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<int, int>",
//                                                       "r" to "Dictionary<int, int>"))
//            {
//                requires("x != null")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ > 0")}")
//                requires("r != null")
//                requires("${forAll("r.Keys", "_ >= 0")}")
//                requires("${forAll("r.Values", "_ > 0")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "map bool to map bool to bool",
//                     parameters = listOf("x" to "map bool to map bool to bool"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<bool, Dictionary<bool, bool>>",
//                                   parameters = listOf("x" to "Dictionary<bool, Dictionary<bool, bool>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x.Values", "_ != null")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("${forAll("$result.Values", "_ != null")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<bool, Dictionary<bool, bool>>",
//                                                       "r" to "Dictionary<bool, Dictionary<bool, bool>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("r != null")
//                requires("${forAll("r.Values", "_ != null")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "map nat to map nat to seq of char",
//                     parameters = listOf("x" to "map nat to map nat to seq of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<int, Dictionary<int, string>>",
//                                   parameters = listOf("x" to "Dictionary<int, Dictionary<int, string>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("${forAll("x.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                requires("${forAll("x.Values", "${forAll("_.Values", "__ != null")}")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("${forAll("$result.Keys", "_ >= 0")}")
//                ensures("${forAll("$result.Values", "_ != null")}")
//                ensures("${forAll("$result.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                ensures("${forAll("$result.Values", "${forAll("_.Values", "__ != null")}")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<int, Dictionary<int, string>>",
//                                                       "r" to "Dictionary<int, Dictionary<int, string>>"))
//            {
//                requires("x != null")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("${forAll("x.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                requires("${forAll("x.Values", "${forAll("_.Values", "__ != null")}")}")
//                requires("r != null")
//                requires("${forAll("r.Keys", "_ >= 0")}")
//                requires("${forAll("r.Values", "_ != null")}")
//                requires("${forAll("r.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                requires("${forAll("r.Values", "${forAll("_.Values", "__ != null")}")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "inmap bool to bool",
//                     parameters = listOf("x" to "inmap bool to bool"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<bool, bool>",
//                                   parameters = listOf("x" to "Dictionary<bool, bool>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.IsInjective()")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<bool, bool>",
//                                                       "r" to "Dictionary<bool, bool>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("r != null")
//                requires("r.IsInjective()")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "inmap nat to nat1",
//                     parameters = listOf("x" to "inmap nat to nat1"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<int, int>",
//                                   parameters = listOf("x" to "Dictionary<int, int>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ > 0")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.IsInjective()")
//                ensures("${forAll("$result.Keys", "_ >= 0")}")
//                ensures("${forAll("$result.Values", "_ > 0")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<int, int>",
//                                                       "r" to "Dictionary<int, int>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ > 0")}")
//                requires("r != null")
//                requires("r.IsInjective()")
//                requires("${forAll("r.Keys", "_ >= 0")}")
//                requires("${forAll("r.Values", "_ > 0")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "inmap bool to inmap bool to bool",
//                     parameters = listOf("x" to "inmap bool to inmap bool to bool"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<bool, Dictionary<bool, bool>>",
//                                   parameters = listOf("x" to "Dictionary<bool, Dictionary<bool, bool>>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("${forAll("x.Values", "_.IsInjective()")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.IsInjective()")
//                ensures("${forAll("$result.Values", "_ != null")}")
//                ensures("${forAll("$result.Values", "_.IsInjective()")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<bool, Dictionary<bool, bool>>",
//                                                       "r" to "Dictionary<bool, Dictionary<bool, bool>>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("${forAll("x.Values", "_.IsInjective()")}")
//                requires("r != null")
//                requires("r.IsInjective()")
//                requires("${forAll("r.Values", "_ != null")}")
//                requires("${forAll("r.Values", "_.IsInjective()")}")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "inmap nat to inmap nat to seq of char",
//                     parameters = listOf("x" to "inmap nat to inmap nat to seq of char"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Dictionary<int, Dictionary<int, string>>",
//                                   parameters = listOf("x" to "Dictionary<int, Dictionary<int, string>>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("${forAll("x.Values", "_.IsInjective()")}")
//                requires("${forAll("x.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                requires("${forAll("x.Values", "${forAll("_.Values", "__ != null")}")}")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.IsInjective()")
//                ensures("${forAll("$result.Keys", "_ >= 0")}")
//                ensures("${forAll("$result.Values", "_ != null")}")
//                ensures("${forAll("$result.Values", "_.IsInjective()")}")
//                ensures("${forAll("$result.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                ensures("${forAll("$result.Values", "${forAll("_.Values", "__ != null")}")}")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Dictionary<int, Dictionary<int, string>>",
//                                                       "r" to "Dictionary<int, Dictionary<int, string>>"))
//            {
//                requires("x != null")
//                requires("x.IsInjective()")
//                requires("${forAll("x.Keys", "_ >= 0")}")
//                requires("${forAll("x.Values", "_ != null")}")
//                requires("${forAll("x.Values", "_.IsInjective()")}")
//                requires("${forAll("x.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                requires("${forAll("x.Values", "${forAll("_.Values", "__ != null")}")}")
//                requires("r != null")
//                requires("r.IsInjective()")
//                requires("${forAll("r.Keys", "_ >= 0")}")
//                requires("${forAll("r.Values", "_ != null")}")
//                requires("${forAll("r.Values", "_.IsInjective()")}")
//                requires("${forAll("r.Values", "${forAll("_.Keys", "__ >= 0")}")}")
//                requires("${forAll("r.Values", "${forAll("_.Values", "__ != null")}")}")
//                returns("true")
//            }
//        }
//    }
//}
