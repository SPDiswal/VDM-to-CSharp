package org.overture.codegen.vdm2cs.integration.types.compound

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek
import org.overture.codegen.vdm2cs.utilities.forAll

class ProductTypeIntegration : TranscompilerIntegrationSpek()
//{
//    init
//    {
//        vdm {
//            function(name = nextPlaceholder, returnType = "nat * nat",
//                     parameters = listOf("x" to "nat * nat"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Tuple<int, int>",
//                                   parameters = listOf("x" to "Tuple<int, int>"))
//            {
//                requires("x != null")
//                requires("x.Item1 >= 0")
//                requires("x.Item2 >= 0")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.Item1 >= 0")
//                ensures("$result.Item2 >= 0")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Tuple<int, int>",
//                                                       "r" to "Tuple<int, int>"))
//            {
//                requires("x != null")
//                requires("x.Item1 >= 0")
//                requires("x.Item2 >= 0")
//                requires("r != null")
//                requires("r.Item1 >= 0")
//                requires("r.Item2 >= 0")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "bool * char * int",
//                     parameters = listOf("x" to "bool * char * int"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Tuple<bool, char, int>",
//                                   parameters = listOf("x" to "Tuple<bool, char, int>"))
//            {
//                requires("x != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Tuple<bool, char, int>",
//                                                       "r" to "Tuple<bool, char, int>"))
//            {
//                requires("x != null")
//                requires("r != null")
//                returns("true")
//            }
//        }
//
//        vdm {
//            function(name = nextPlaceholder, returnType = "seq of char * set of nat * rat * nat1 * real",
//                     parameters = listOf("x" to "seq of char * set of nat * rat * nat1 * real"))
//        } becomes cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Tuple<string, HashSet<int>, decimal, int, decimal>",
//                                   parameters = listOf("x" to "Tuple<string, HashSet<int>, decimal, int, decimal>"))
//            {
//                requires("x != null")
//                requires("x.Item1 != null")
//                requires("x.Item2 != null")
//                requires("${forAll("x.Item2", "_ >= 0")}")
//                requires("x.Item4 > 0")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.Item1 != null")
//                ensures("$result.Item2 != null")
//                ensures("${forAll("$result.Item2", "_ >= 0")}")
//                ensures("$result.Item4 > 0")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Tuple<string, HashSet<int>, decimal, int, decimal>",
//                                                       "r" to "Tuple<string, HashSet<int>, decimal, int, decimal>"))
//            {
//                requires("x != null")
//                requires("x.Item1 != null")
//                requires("x.Item2 != null")
//                requires("${forAll("x.Item2", "_ >= 0")}")
//                requires("x.Item4 > 0")
//                requires("r != null")
//                requires("r.Item1 != null")
//                requires("r.Item2 != null")
//                requires("${forAll("r.Item2", "_ >= 0")}")
//                requires("r.Item4 > 0")
//                returns("true")
//            }
//        }
//
//        // TODO Presumably, there is a bug in Overture that prevents this test from passing.
//        vdm {
//            function(name = nextPlaceholder, returnType = "(nat * nat) * (real * seq of char)",
//                     parameters = listOf("x" to "(nat * nat) * (real * seq of char)"))
//        } to cs {
//            purePublicStaticMethod(name = placeholder, returnType = "Tuple<Tuple<int, int>, Tuple<decimal, string>>",
//                                   parameters = listOf("x" to "Tuple<Tuple<int, int>, Tuple<decimal, string>>"))
//            {
//                requires("x != null")
//                requires("x.Item1 != null")
//                requires("x.Item1.Item1 >= 0")
//                requires("x.Item1.Item2 >= 0")
//                requires("x.Item2 != null")
//                requires("x.Item2.Item2 != null")
//                ensures("Post$placeholder(x, $result)")
//                ensures("$result != null")
//                ensures("$result.Item1 != null")
//                ensures("$result.Item1.Item1 >= 0")
//                ensures("$result.Item1.Item2 >= 0")
//                ensures("$result.Item2 != null")
//                ensures("$result.Item2.Item2 != null")
//                throwsNotImplementedException()
//            }
//
//            purePublicStaticMethod(name = "Post$placeholder", returnType = "bool",
//                                   parameters = listOf("x" to "Tuple<Tuple<int, int>, Tuple<decimal, string>>",
//                                                       "r" to "Tuple<Tuple<int, int>, Tuple<decimal, string>>"))
//            {
//                requires("x != null")
//                requires("x.Item1 != null")
//                requires("x.Item1.Item1 >= 0")
//                requires("x.Item1.Item2 >= 0")
//                requires("x.Item2 != null")
//                requires("x.Item2.Item2 != null")
//                requires("r != null")
//                requires("r.Item1 != null")
//                requires("r.Item1.Item1 >= 0")
//                requires("r.Item1.Item2 >= 0")
//                requires("r.Item2 != null")
//                requires("r.Item2.Item2 != null")
//                returns("true")
//            }
//        }
//    }
//}
