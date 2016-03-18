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

                it("has a using directive for 'System'")
                {
                    cs.shouldContain(aUsingDirective("System"))
                }

                it("has a using directive for 'System.Diagnostics.Contracts'")
                {
                    cs.shouldContain(aUsingDirective("System.Diagnostics.Contracts"))
                }

                it("declares the namespace 'VdmToCs'")
                {
                    cs.shouldContain(aNamespaceDeclaration("VdmToCs"))
                }

                it("declares the static class 'DEFAULT'")
                {
                    cs.shouldContain(inNamespace("VdmToCs") { aStaticClassDeclaration("DEFAULT") })
                }
            }
        }
    }
}
