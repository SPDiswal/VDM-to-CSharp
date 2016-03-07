package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class UnionTypesSpecification : TranscompilerSpecification()
{
    // TODO Use the most specific common type in a union type instead of object.
    // E.g. nat | int becomes int, int | real becomes double (although such union types are redundant).

    /**
     * The VDM union type of bool and nat is transcompiled to the C# object type.
     */
    @Test
    fun boolNatUnion_BecomesObject()
    {
        val boolNatUnion = transcompileSingleDocument("""
            functions
                Alpha () u: bool | nat
                post true;
            """)

        boolNatUnion.shouldContain(a("public static object Alpha()"))
    }

    /**
     * The VDM union type of int, char and seq of nat is transcompiled to the C# object type.
     */
    @Test
    fun intCharSeqOfNatUnion_BecomesObject()
    {
        val intCharSeqOfNatUnion = transcompileSingleDocument("""
            functions
                Bravo () u: int | char | seq of nat
                post true;
            """)

        intCharSeqOfNatUnion.shouldContain(a("public static object Bravo()"))
    }

    /**
     * The VDM seq type of union of bool and nat is transcompiled to the C# IList<object> type.
     */
    @Test
    fun seqOfBoolNatUnion_BecomesObject()
    {
        val seqOfBoolNatUnion = transcompileSingleDocument("""
            functions
                Charlie () u: seq of (bool | nat)
                post true;
            """)

        seqOfBoolNatUnion.shouldContain(a("public static IList<object> Charlie()"))
    }
}
