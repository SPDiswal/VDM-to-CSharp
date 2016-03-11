package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class ProductTypes : Spek()
{
    // TODO: Support tuples with more than seven items (the C# Tuple type only supports up to seven items by default).

    init
    {
        given("an implicit function with a return type of nat * nat")
        {
            val vdm = """
                functions
                    Alpha () p: nat * nat
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of Tuple<int, int>") { cs.shouldContain(a("Tuple<int, int> Alpha()")) }
            }
        }

        given("an implicit function with a return type of bool * char * int")
        {
            val vdm = """
                functions
                    Bravo () p: bool * char * int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of Tuple<bool, char, int>")
                {
                    cs.shouldContain(a("Tuple<bool, char, int> Bravo()"))
                }
            }
        }

        given("an implicit function with a return type of seq of char * set of int * nat1 * rat * real")
        {
            val vdm = """
                functions
                    Charlie () p: seq of char * set of int * nat1 * rat * real
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of Tuple<string, ISet<int>, int, double, double>")
                {
                    cs.shouldContain(a("Tuple<string, ISet<int>, int, double, double> Charlie()"))
                }
            }
        }

        given("an implicit function with a return type of (nat * nat) * (real * real)")
        {
            val vdm = """
                functions
                    Delta () p: (nat * nat) * (real * real)
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of Tuple<Tuple<int, int>, Tuple<double, double>>")
                {
                    cs.shouldContain(a("Tuple<Tuple<int, int>, Tuple<double, double>> Delta()"))
                }
            }
        }
    }
}
