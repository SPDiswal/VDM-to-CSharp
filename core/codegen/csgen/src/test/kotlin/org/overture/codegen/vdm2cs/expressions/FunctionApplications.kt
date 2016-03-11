package org.overture.codegen.vdm2cs.expressions

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class FunctionApplications : Spek()
{
    // TODO Function applications with arguments

    init
    {
        given("a function 'Alpha' that invokes another function 'Bravo'")
        {
            val vdm = """
                functions
                    Alpha: () -> bool
                    Alpha() ==
                        Bravo();

                    Bravo () b: bool
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("invokes 'Bravo' in 'Alpha'")
                {
                    cs.shouldContain(a("bool Alpha()") { a("return Bravo();") })
                }
            }
        }
    }
}
