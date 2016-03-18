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

                it("declares the static method 'Alpha' with " +
                   "a [Pure] attribute, no parameters and a return type of int")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration("int", "Alpha"))
                }

                it("returns 1 in 'Alpha'")
                {
                    cs.shouldContain(inMethod("int", "Alpha") { a("return 1;") })
                }
            }
        }
    }
}
