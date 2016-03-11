package org.overture.codegen.vdm2cs.declarations

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class FlatSpecifications : Spek()
{
    init
    {
        given("a flat VDM-SL specification")
        {
            val vdm = """
                functions
                    Alpha () b: bool
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has using statements")
                {
                    cs.shouldContain(
                        a("""
                        using System;
                        using System.Diagnostics.Contracts;
                        """))
                }

                it("has a namespace called 'VdmToCSharp'")
                {
                    cs.shouldContain(
                        a("namespace VdmToCSharp") { anything() })
                }

                it("has a static class called 'DEFAULT'")
                {
                    cs.shouldContain(
                        a("public static class DEFAULT") { anything() })
                }
            }
        }
    }
}
