package org.overture.codegen.vdm2cs.declarations

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class ImplicitFunctions : Spek()
{
    init
    {
        given("an implicit function 'Alpha' with no parameters and a return type of nat; " +
              "and a postcondition 'post_Alpha' that returns true")
        {
            val vdm = """
                functions
                    Alpha () n: nat
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a static method called 'Alpha' with " +
                   "a [Pure] attribute, no parameters and a return type of int")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static int Alpha()") { anything() })
                }

                it("invokes 'post_Alpha' by Contract.Ensures in 'Alpha'")
                {
                    cs.shouldContain(
                        a("int Alpha()")
                        {
                            a("Contract.Ensures(post_Alpha(Contract.Result<int>()));") +
                            anything()
                        })
                }

                it("throws a NotImplementedException in 'Alpha'")
                {
                    cs.shouldContain(
                        a("int Alpha()")
                        {
                            anything() +
                            a("throw new NotImplementedException();")
                        })
                }

                it("has a static method called 'post_Alpha' with " +
                   "a [Pure] attribute, a parameter of int and a return type of bool")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static bool post_Alpha(int n)") { anything() })
                }

                it("returns true in 'post_Alpha'")
                {
                    cs.shouldContain(
                        a("bool post_Alpha(int n)") { a("return true;") })
                }
            }
        }

        given("an implicit function 'Bravo' with a parameter of int and a return type of real; " +
              "and a precondition 'pre_Bravo' that returns true; " +
              "and a postcondition 'post_Bravo' that returns false")
        {
            val vdm = """
                functions
                    Bravo (a: int) r: real
                    pre true
                    post false;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a static method called 'Bravo' with " +
                   "a [Pure] attribute, a parameter of int and a return type of double")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static double Bravo(int a)") { anything() })
                }

                it("invokes 'pre_Bravo' by Contract.Requires in 'Bravo'")
                {
                    cs.shouldContain(
                        a("double Bravo(int a)")
                        {
                            a("Contract.Requires(pre_Bravo(a));") +
                            anything()
                        })
                }

                it("invokes 'post_Bravo' by Contract.Ensures in 'Bravo'")
                {
                    cs.shouldContain(
                        a("double Bravo(int a)")
                        {
                            anything() +
                            a("Contract.Ensures(post_Bravo(a, Contract.Result<double>()));") +
                            anything()
                        })
                }

                it("throws a NotImplementedException in 'Bravo'")
                {
                    cs.shouldContain(
                        a("double Bravo(int a)")
                        {
                            anything() +
                            a("throw new NotImplementedException();")
                        })
                }

                it("has a static method called 'pre_Bravo' with " +
                   "a [Pure] attribute, a parameter of int and a return type of bool")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static bool pre_Bravo(int a)") { anything() })
                }

                it("returns true in 'pre_Bravo'")
                {
                    cs.shouldContain(
                        a("bool pre_Bravo(int a)") { a("return true;") })
                }

                it("has a static method called 'post_Bravo' with " +
                   "a [Pure] attribute, parameters of int and double and a return type of bool")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static bool post_Bravo(int a, double r)") { anything() })
                }

                it("returns false in 'post_Bravo'")
                {
                    cs.shouldContain(
                        a("bool post_Bravo(int a, double r)") { a("return false;") })
                }
            }
        }

        given("an implicit function 'Charlie' with parameters of nat1, rat and char and a return type of token; " +
              "and a precondition 'pre_Charlie' that returns false; " +
              "and a postcondition 'post_Charlie' that returns 1 + 1 = 2; " +
              "an implicit function 'Delta' with a parameter of seq of char and a return type of seq of char; " +
              "and a postcondition 'post_Delta' that returns i = o")
        {
            val vdm = """
                functions
                    Charlie (x: nat1, y: rat, z: char) t: token
                    pre false
                    post 1 + 1 = 2;

                    Delta (i: seq of char) o: seq of char
                    post i = o;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a static method called 'Charlie' with " +
                   "a [Pure] attribute, parameters of int, double and char and a return type of object")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static object Charlie(int x, double y, char z)") { anything() })
                }

                it("invokes 'pre_Charlie' by Contract.Requires in 'Charlie'")
                {
                    cs.shouldContain(
                        a("object Charlie(int x, double y, char z)")
                        {
                            a("Contract.Requires(pre_Charlie(x, y, z));") +
                            anything()
                        })
                }

                it("invokes 'post_Charlie' by Contract.Ensures in 'Charlie'")
                {
                    cs.shouldContain(
                        a("object Charlie(int x, double y, char z)")
                        {
                            anything() +
                            a("Contract.Ensures(post_Charlie(x, y, z, Contract.Result<object>()));") +
                            anything()
                        })
                }

                it("throws a NotImplementedException in 'Charlie'")
                {
                    cs.shouldContain(
                        a("object Charlie(int x, double y, char z)")
                        {
                            anything() +
                            a("throw new NotImplementedException();")
                        })
                }

                it("has a static method called 'pre_Charlie' with " +
                   "a [Pure] attribute, parameters of int, double and char and a return type of bool")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static bool pre_Charlie(int x, double y, char z)") { anything() })
                }

                it("returns false in 'pre_Charlie'")
                {
                    cs.shouldContain(
                        a("bool pre_Charlie(int x, double y, char z)") { a("return false;") })
                }

                it("has a static method called 'post_Charlie' with " +
                   "a [Pure] attribute, parameters of int, double, char and object and a return type of bool")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static bool post_Charlie(int x, double y, char z, object t)") { anything() })
                }

                it("returns 1 + 1 == 2 in 'post_Charlie'")
                {
                    cs.shouldContain(
                        a("bool post_Charlie(int x, double y, char z, object t)") { a("return 1 + 1 == 2;") })
                }

                it("has a static method called 'Delta' with " +
                   "a [Pure] attribute, a parameter of string and a return type of string")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static string Delta(string i)") { anything() })
                }

                it("invokes 'post_Delta' by Contract.Ensures in 'Delta'")
                {
                    cs.shouldContain(
                        a("string Delta(string i)")
                        {
                            a("Contract.Ensures(post_Delta(i, Contract.Result<string>()));") +
                            anything()
                        })
                }

                it("throws a NotImplementedException in 'Delta'")
                {
                    cs.shouldContain(
                        a("string Delta(string i)")
                        {
                            anything() +
                            a("throw new NotImplementedException();")
                        })
                }

                it("has a static method called 'post_Delta' with " +
                   "a [Pure] attribute, parameters of string and string and a return type of bool")
                {
                    cs.shouldContain(
                        a("[Pure]",
                          "public static bool post_Delta(string i, string o)") { anything() })
                }

                it("returns i == o in 'post_Delta'")
                {
                    cs.shouldContain(
                        a("bool post_Delta(string i, string o)") { a("return i == o;") })
                }
            }
        }
    }
}
