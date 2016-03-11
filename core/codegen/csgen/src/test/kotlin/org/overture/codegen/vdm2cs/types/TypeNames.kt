package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.junit.Test
import org.overture.codegen.vdm2cs.utilities.*

final class TypeNames : Spek()
{
    // TODO Test type names with invariants to see that invariants are preserved upon inlining the type alias
    //      perhaps by adding pre- and postconditions to function/operation parameters in the transcompiled code.

    init
    {
        given("a type name 'BoolAlias' for bool " +
              "and an implicit function with a return type of BoolAlias")
        {
            val vdm = """
                types
                    BoolAlias = bool;

                functions
                    Alpha () b: BoolAlias
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of bool") { cs.shouldContain(a("bool Alpha()")) }
            }
        }

        given("a type name 'SetOfNatAlias' for set of nat " +
              "and an implicit function with a return type of SetOfNatAlias")
        {
            val vdm = """
                types
                    SetOfNatAlias = set of nat;

                functions
                    Bravo () s: SetOfNatAlias
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of ISet<int>") { cs.shouldContain(a("ISet<int> Bravo()")) }
            }
        }

        given("a type name 'BoolAlias' for bool " +
              "and another type name 'AnotherBoolAlias' for BoolAlias " +
              "and an implicit function with a return type of AnotherBoolAlias")
        {
            val vdm = """
                types
                    BoolAlias = bool;
                    AnotherBoolAlias = BoolAlias;

                functions
                    Charlie () b: AnotherBoolAlias
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of bool") { cs.shouldContain(a("bool Charlie()")) }
            }
        }

        given("a type name 'NatAlias' for nat " +
              "and another type name 'SetOfNatAlias' for set of NatAlias " +
              "and another type name 'AnotherSetOfNatAlias' for set of SetOfNatAlias " +
              "and an implicit function with a return type of AnotherSetOfNatAlias")
        {
            val vdm = """
                types
                    NatAlias = nat;
                    SetOfNatAlias = set of NatAlias;
                    AnotherSetOfNatAlias = SetOfNatAlias;

                functions
                    Delta () s: AnotherSetOfNatAlias
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of ISet<int>") { cs.shouldContain(a("ISet<int> Delta()")) }
            }
        }
    }
}
