package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class MapTypes : Spek()
{
    init
    {
        given("an implicit function with a return type of map char to bool")
        {
            val vdm = """
                functions
                    Alpha () m: map char to bool
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IDictionary<char, bool>")
                {
                    cs.shouldContain(a("IDictionary<char, bool> Alpha()"))
                }
            }
        }

        given("an implicit function with a return type of map int to int")
        {
            val vdm = """
                functions
                    Bravo () m: map int to int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IDictionary<int, int>")
                {
                    cs.shouldContain(a("IDictionary<int, int> Bravo()"))
                }
            }
        }

        given("an implicit function with a return type of map real to map int to int")
        {
            val vdm = """
                functions
                    Charlie () m: map real to map int to int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IDictionary<double, IDictionary<int, int>>")
                {
                    cs.shouldContain(a("IDictionary<double, IDictionary<int, int>> Charlie()"))
                }
            }
        }

        given("an implicit function with a return type of inmap char to bool")
        {
            val vdm = """
                functions
                    Delta () m: inmap char to bool
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IDictionary<char, bool>")
                {
                    cs.shouldContain(a("IDictionary<char, bool> Delta()"))
                }
            }
        }

        given("an implicit function with a return type of inmap int to int")
        {
            val vdm = """
                functions
                    Echo () m: inmap int to int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IDictionary<int, int>")
                {
                    cs.shouldContain(a("IDictionary<int, int> Echo()"))
                }
            }
        }

        given("an implicit function with a return type of inmap real to map int to int")
        {
            val vdm = """
                functions
                    Foxtrot () m: inmap real to map int to int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IDictionary<double, IDictionary<int, int>>")
                {
                    cs.shouldContain(a("IDictionary<double, IDictionary<int, int>> Foxtrot()"))
                }
            }
        }
    }
}
