package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class TypeNamesSpecification : TranscompilerSpecification()
{
    // TODO Test type names with invariants to see that invariants are preserved upon inlining the type alias
    //      perhaps by adding pre- and postconditions to function/operation parameters in the transcompiled code.

    /**
     * A VDM type name for a bool type is transcompiled to the C# bool type.
     */
    @Test
    fun nameForBool_BecomesBool()
    {
        val nameForBool = transcompileSingleDocument("""
            types
                BoolAlias = bool;

            functions
                Alpha () b: BoolAlias
                post true;
            """)

        nameForBool.shouldContain(a("public static bool Alpha()"))
    }

    /**
     * A VDM type name for a set of nat type is transcompiled to the C# ISet<int> type.
     */
    @Test
    fun nameForSetOfNat_BecomesISetInt()
    {
        val nameForSetOfNat = transcompileSingleDocument("""
            types
                SetOfNatAlias = set of nat;

            functions
                Bravo () s: SetOfNatAlias
                post true;
            """)

        nameForSetOfNat.shouldContain(a("public static ISet<int> Bravo()"))
    }

    /**
     * A VDM type name for a type name for a bool type is transcompiled to the C# bool type.
     */
    @Test
    fun nameForNameForBool_BecomesBool()
    {
        val nameForNameForBool = transcompileSingleDocument("""
            types
                BoolAlias = bool;
                AnotherBoolAlias = BoolAlias;

            functions
                Charlie () b: AnotherBoolAlias
                post true;
            """)

        nameForNameForBool.shouldContain(a("public static bool Charlie()"))
    }

    /**
     * A VDM type name for a type name for a set of a type name for a nat type is transcompiled
     * to the C# ISet<int> type.
     */
    @Test
    fun nameForNameForSetOfNameForNat_BecomesISetInt()
    {
        val nameForNameForSetOfNameForNat = transcompileSingleDocument("""
            types
                NatAlias = nat;
                SetOfNatAlias = set of NatAlias;
                AnotherSetOfNatAlias = SetOfNatAlias;

            functions
                Delta () s: AnotherSetOfNatAlias
                post true;
            """)

        nameForNameForSetOfNameForNat.shouldContain(a("public static ISet<int> Delta()"))
    }
}
