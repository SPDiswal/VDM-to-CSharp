package org.overture.codegen.vdm2cs.declarations

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class ExplicitFunctions : Spek()
{
    init
    {
        given("an explicit function 'Alpha' with no parameters that returns a nat of 1")
        {
            val vdm = """
                functions
                    Alpha: () -> nat
                    Alpha() == 1;
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

                it("returns 1 in 'Alpha'")
                {
                    cs.shouldContain(a("int Alpha()") { a("return 1;") })
                }
            }
        }
    }
}
