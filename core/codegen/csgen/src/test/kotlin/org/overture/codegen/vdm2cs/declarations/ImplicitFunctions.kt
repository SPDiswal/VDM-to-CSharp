package org.overture.codegen.vdm2cs.declarations

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class ImplicitFunctions : Spek()
{
    // TODO Use code contracts to enforce typing rules of VDM onto parameter values (preconditions),
    //      return values (postconditions) and fields/properties (invariants).
    //
    //      Guard nat by >= 0 and nat1 by > 0.
    //      Guard all non-optional types by != null.
    //      Guard seq1 by Any().
    //      Guard seq1 of char (string) by !IsEmpty().
    //      Guard inmap by IsInjective() (custom extension method).

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

                it("declares the static method 'Alpha' with " +
                   "a [Pure] attribute, no parameters and a return type of int")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration("int", "Alpha"))
                }

                it("invokes 'post_Alpha' by Contract.Ensures in 'Alpha'")
                {
                    cs.shouldContain(
                        inTopOfMethod("int", "Alpha")
                        {
                            a("Contract.Ensures(post_Alpha(Contract.Result<int>()));")
                        })
                }

                it("throws a NotImplementedException in 'Alpha'")
                {
                    cs.shouldContain(
                        inBottomOfMethod("int", "Alpha")
                        {
                            a("throw new NotImplementedException();")
                        })
                }

                it("declares the static method 'post_Alpha' with " +
                   "a [Pure] attribute, a parameter of int and a return type of bool")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("bool", "post_Alpha", listOf(Pair("int", "n"))))
                }

                it("returns true in 'post_Alpha'")
                {
                    cs.shouldContain(
                        inMethod("bool", "post_Alpha", listOf(Pair("int", "n")))
                        {
                            a("return true;")
                        })
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

                it("declares the static method 'Bravo' with " +
                   "a [Pure] attribute, a parameter of int and a return type of double")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("double", "Bravo", listOf(Pair("int", "a"))))
                }

                it("invokes 'pre_Bravo' by Contract.Requires in 'Bravo'")
                {
                    cs.shouldContain(
                        inTopOfMethod("double", "Bravo", listOf(Pair("int", "a")))
                        {
                            a("Contract.Requires(pre_Bravo(a));")
                        })
                }

                it("invokes 'post_Bravo' by Contract.Ensures in 'Bravo'")
                {
                    cs.shouldContain(
                        inMidstOfMethod("double", "Bravo", listOf(Pair("int", "a")))
                        {
                            a("Contract.Ensures(post_Bravo(a, Contract.Result<double>()));")
                        })
                }

                it("throws a NotImplementedException in 'Bravo'")
                {
                    cs.shouldContain(
                        inBottomOfMethod("double", "Bravo", listOf(Pair("int", "a")))
                        {
                            a("throw new NotImplementedException();")
                        })
                }

                it("declares the static method 'pre_Bravo' with " +
                   "a [Pure] attribute, a parameter of int and a return type of bool")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("bool", "pre_Bravo", listOf(Pair("int", "a"))))
                }

                it("returns true in 'pre_Bravo'")
                {
                    cs.shouldContain(
                        inMethod("bool", "pre_Bravo", listOf(Pair("int", "a")))
                        {
                            a("return true;")
                        })
                }

                it("declares the static method 'post_Bravo' with " +
                   "a [Pure] attribute, parameters of int and double and a return type of bool")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("bool", "post_Bravo", listOf(Pair("int", "a"),
                                                                                  Pair("double", "r"))))
                }

                it("returns false in 'post_Bravo'")
                {
                    cs.shouldContain(
                        inMethod("bool", "post_Bravo", listOf(Pair("int", "a"),
                                                              Pair("double", "r")))
                        {
                            a("return false;")
                        })
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

                it("declares the static method 'Charlie' with " +
                   "a [Pure] attribute, parameters of int, double and char and a return type of Token")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("Token", "Charlie", listOf(Pair("int", "x"),
                                                                                Pair("double", "y"),
                                                                                Pair("char", "z"))))
                }

                it("invokes 'pre_Charlie' by Contract.Requires in 'Charlie'")
                {
                    cs.shouldContain(
                        inTopOfMethod("Token", "Charlie", listOf(Pair("int", "x"),
                                                                 Pair("double", "y"),
                                                                 Pair("char", "z")))
                        {
                            a("Contract.Requires(pre_Charlie(x, y, z));")
                        })
                }

                it("invokes 'post_Charlie' by Contract.Ensures in 'Charlie'")
                {
                    cs.shouldContain(
                        inMidstOfMethod("Token", "Charlie", listOf(Pair("int", "x"),
                                                                   Pair("double", "y"),
                                                                   Pair("char", "z")))
                        {
                            a("Contract.Ensures(post_Charlie(x, y, z, Contract.Result<Token>()));")
                        })
                }

                it("throws a NotImplementedException in 'Charlie'")
                {
                    cs.shouldContain(
                        inBottomOfMethod("Token", "Charlie", listOf(Pair("int", "x"),
                                                                    Pair("double", "y"),
                                                                    Pair("char", "z")))
                        {
                            a("throw new NotImplementedException();")
                        })
                }

                it("declares the static method 'pre_Charlie' with " +
                   "a [Pure] attribute, parameters of int, double and char and a return type of bool")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("bool", "pre_Charlie", listOf(Pair("int", "x"),
                                                                                   Pair("double", "y"),
                                                                                   Pair("char", "z"))))
                }

                it("returns false in 'pre_Charlie'")
                {
                    cs.shouldContain(
                        inMethod("bool", "pre_Charlie", listOf(Pair("int", "x"),
                                                               Pair("double", "y"),
                                                               Pair("char", "z")))
                        {
                            a("return false;")
                        })
                }

                it("declares the static method 'post_Charlie' with " +
                   "a [Pure] attribute, parameters of int, double, char and Token and a return type of bool")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("bool", "post_Charlie", listOf(Pair("int", "x"),
                                                                                    Pair("double", "y"),
                                                                                    Pair("char", "z"),
                                                                                    Pair("Token", "t"))))
                }

                it("returns 1 + 1 == 2 in 'post_Charlie'")
                {
                    cs.shouldContain(
                        inMethod("bool", "post_Charlie", listOf(Pair("int", "x"),
                                                                Pair("double", "y"),
                                                                Pair("char", "z"),
                                                                Pair("Token", "t")))
                        {
                            a("return 1 + 1 == 2;")
                        })
                }

                it("declares the static method 'Delta' with " +
                   "a [Pure] attribute, a parameter of string and a return type of string")
                {
                    cs.shouldContain(
                        aPureStaticMethodDeclaration("string", "Delta", listOf(Pair("string", "i"))))
                }

                it("invokes 'post_Delta' by Contract.Ensures in 'Delta'")
                {
                    cs.shouldContain(
                        inTopOfMethod("string", "Delta", listOf(Pair("string", "i")))
                        {
                            a("Contract.Ensures(post_Delta(i, Contract.Result<string>()));")
                        })
                }

                it("throws a NotImplementedException in 'Delta'")
                {
                    cs.shouldContain(
                        inBottomOfMethod("string", "Delta", listOf(Pair("string", "i")))
                        {
                            a("throw new NotImplementedException();")
                        })
                }

                it("declares the static method 'post_Delta' with " +
                   "a [Pure] attribute, parameters of string and string and a return type of bool")
                {
                    aPureStaticMethodDeclaration("bool", "post_Delta", listOf(Pair("string", "i"),
                                                                              Pair("string", "o")))
                }

                it("returns i == o in 'post_Delta'")
                {
                    cs.shouldContain(
                        inMethod("bool", "post_Delta", listOf(Pair("string", "i"),
                                                              Pair("string", "o")))
                        {
                            a("return i == o;")
                        })
                }
            }
        }
    }
}
