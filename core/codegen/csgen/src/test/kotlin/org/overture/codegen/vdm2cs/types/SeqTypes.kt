package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class SeqTypes : Spek()
{
    init
    {
        given("an implicit function with a return type of seq of char")
        {
            val vdm = """
                functions
                    Alpha () s: seq of char
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of string") { cs.shouldContain(a("string Alpha()")) }
            }
        }

        given("an implicit function with a return type of seq of int")
        {
            val vdm = """
                functions
                    Bravo () s: seq of int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IList<int>") { cs.shouldContain(a("IList<int> Bravo()")) }
            }
        }

        given("an implicit function with a return type of seq of seq of real")
        {
            val vdm = """
                functions
                    Charlie () s: seq of seq of real
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IList<IList<double>>")
                {
                    cs.shouldContain(a("IList<IList<double>> Charlie()"))
                }
            }
        }

        given("an implicit function with a return type of seq1 of char")
        {
            val vdm = """
                functions
                    Delta () s: seq1 of char
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of string")
                {
                    cs.shouldContain(a("string Delta()"))
                }
            }
        }

        given("an implicit function with a return type of seq1 of int")
        {
            val vdm = """
                functions
                    Echo () s: seq1 of int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IList<int>")
                {
                    cs.shouldContain(a("IList<int> Echo()"))
                }
            }
        }

        given("an implicit function with a return type of seq1 of seq of real")
        {
            val vdm = """
                functions
                    Foxtrot () s: seq1 of seq of real
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IList<IList<double>>")
                {
                    cs.shouldContain(a("IList<IList<double>> Foxtrot()"))
                }
            }
        }
    }
}
