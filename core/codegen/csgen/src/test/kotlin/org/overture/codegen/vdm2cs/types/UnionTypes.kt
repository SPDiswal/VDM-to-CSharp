package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class UnionTypes : Spek()
{
    // TODO Use the most specific common type in a union type instead of object.
    // E.g. nat | int becomes int, int | real becomes double (although such union types are redundant).

    // TODO Represent union types by dedicated classes that are generated on demand (like records).
    //      Let them have a single object property holding the current value.
    //      Use some kind of naming scheme for anonymous union types.

    init
    {
        given("an implicit function with a return type of bool | nat")
        {
            val vdm = """
                functions
                    Alpha () u: bool | nat
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of object")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration("object", "Alpha"))
                }
            }
        }

        given("an implicit function with a return type of int | char | seq of nat")
        {
            val vdm = """
                functions
                    Bravo () u: int | char | seq of nat
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of object")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration("object", "Bravo"))
                }
            }
        }

        given("an implicit function with a return type of seq of (bool | nat)")
        {
            val vdm = """
                functions
                    Charlie () u: seq of (bool | nat)
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of IList<object>")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration("IList<object>", "Charlie"))
                }
            }
        }
    }
}
