package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class SetTypes : Spek()
{
    init
    {
        given("an implicit function with a return type of set of char")
        {
            val vdm = """
                functions
                    Alpha () s: set of char
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of ISet<char>") { cs.shouldContain(a("ISet<char> Alpha()")) }
            }
        }

        given("an implicit function with a return type of set of int")
        {
            val vdm = """
                functions
                    Bravo () s: set of int
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of ISet<int>") { cs.shouldContain(a("ISet<int> Bravo()")) }
            }
        }

        given("an implicit function with a return type of set of set of real")
        {
            val vdm = """
                functions
                    Charlie () s: set of set of real
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of ISet<ISet<double>>") { cs.shouldContain(a("ISet<ISet<double>> Charlie()")) }
            }
        }
    }
}
