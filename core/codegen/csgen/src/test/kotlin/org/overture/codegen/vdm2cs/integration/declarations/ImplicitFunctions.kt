package org.overture.codegen.vdm2cs.integration.declarations

import org.jetbrains.spek.api.Spek

final class ImplicitFunctions : Spek()
{
    init
    {
//        vdm {
//            function("Alpha", "bool", "x" to "bool")
//        } to cs {
//            pureStaticMethod("Alpha", "bool", "x" to "bool")
//            {
//                ensures("post_Alpha(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            pureStaticMethod("post_Alpha", "bool", "x" to "bool", "r" to "bool")
//            {
//                returns("true")
//            }
//        }
//
//        vdm {
//            function("Bravo", "bool", "x" to "bool")
//            {
//                pre("true")
//                post("1 + 1 = 2")
//            }
//        } to cs {
//            pureStaticMethod("Bravo", "bool", "x" to "bool")
//            {
//                requires("pre_Bravo(x)")
//                ensures("post_Bravo(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            pureStaticMethod("pre_Bravo", "bool", "x" to "bool")
//            {
//                returns("true")
//            }
//
//            pureStaticMethod("post_Bravo", "bool", "x" to "bool", "r" to "bool")
//            {
//                returns("1 + 1 == 2")
//            }
//        }
//
//        vdm {
//            function("Charlie", "int", "x" to "int", "y" to "int")
//            {
//                pre("x < y")
//                post("x + y > r")
//            }
//
//            function("Delta", "seq of char", "x" to "seq of char")
//            {
//                post("r = x")
//            }
//        } to cs {
//            pureStaticMethod("Charlie", "int", "x" to "int", "y" to "int")
//            {
//                requires("pre_Charlie(x, y)")
//                ensures("post_Charlie(x, y, $result)")
//                throwsNotImplementedException()
//            }
//
//            pureStaticMethod("pre_Charlie", "bool", "x" to "int", "y" to "int")
//            {
//                returns("x < y")
//            }
//
//            pureStaticMethod("post_Charlie", "bool", "x" to "int", "y" to "int", "r" to "int")
//            {
//                returns("x + y > r")
//            }
//
//            pureStaticMethod("Delta", "string", "x" to "string")
//            {
//                ensures("post_Delta(x, $result)")
//                throwsNotImplementedException()
//            }
//
//            pureStaticMethod("post_Delta", "bool", "x" to "string", "r" to "string")
//            {
//                returns("r == x")
//            }
//        }
    }
}
