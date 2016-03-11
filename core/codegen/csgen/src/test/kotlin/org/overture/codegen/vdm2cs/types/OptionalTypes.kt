package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.junit.Test
import org.overture.codegen.vdm2cs.utilities.*

final class OptionalTypes : Spek()
{
    init
    {
        given("an implicit function with a return type of [bool]")
        {
            val vdm = """
                functions
                    Alpha () b: [bool]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of bool?") { cs.shouldContain(a("bool? Alpha()")) }
            }
        }

        given("an implicit function with a return type of [char]")
        {
            val vdm = """
                functions
                    Bravo () c: [char]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of char?") { cs.shouldContain(a("char? Bravo()")) }
            }
        }

        given("an implicit function with a return type of [int]")
        {
            val vdm = """
                functions
                    Charlie () i: [int]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of int?") { cs.shouldContain(a("int? Charlie()")) }
            }
        }

        given("an implicit function with a return type of [nat]")
        {
            val vdm = """
                functions
                    Delta () n: [nat]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of int?") { cs.shouldContain(a("int? Delta()")) }
            }
        }

        given("an implicit function with a return type of [nat1]")
        {
            val vdm = """
                functions
                    Echo () n: [nat1]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of int?") { cs.shouldContain(a("int? Echo()")) }
            }
        }

        given("an implicit function with a return type of [rat]")
        {
            val vdm = """
                functions
                    Foxtrot () r: [rat]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of double?") { cs.shouldContain(a("double? Foxtrot()")) }
            }
        }

        given("an implicit function with a return type of [real]")
        {
            val vdm = """
                functions
                    Golf () r: [real]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of double?") { cs.shouldContain(a("double? Golf()")) }
            }
        }

        given("an implicit function with a return type of [token]")
        {
            val vdm = """
                functions
                    Hotel () t: [token]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of object") { cs.shouldContain(a("object Hotel()")) }
            }
        }

        given("an implicit function with a return type of [seq of char]")
        {
            val vdm = """
                functions
                    India () s: [seq of char]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of string") { cs.shouldContain(a("string India()")) }
            }
        }

        given("an implicit function with a return type of [seq of [int]]")
        {
            val vdm = """
                functions
                    Juliet () s: [seq of [int]]
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IList<int?>") { cs.shouldContain(a("IList<int?> Juliet()")) }
            }
        }
    }
}
