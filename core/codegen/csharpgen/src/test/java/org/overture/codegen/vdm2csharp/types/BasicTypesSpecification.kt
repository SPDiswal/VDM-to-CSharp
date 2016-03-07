package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class BasicTypesSpecification : TranscompilerSpecification()
{
    /**
     * The VDM bool basic type is transcompiled to the C# bool type.
     */
    @Test
    fun bool_BecomesBool()
    {
        val bool = transcompileSingleDocument("""
            functions
                Alpha () b: bool
                post true;
            """)

        bool.shouldContain(a("public static bool Alpha()"))
    }

    /**
     * The VDM char basic type is transcompiled to the C# char type.
     */
    @Test
    fun char_BecomesChar()
    {
        val char = transcompileSingleDocument("""
            functions
                Bravo () c: char
                post true;
            """)

        char.shouldContain(a("public static char Bravo()"))
    }

    /**
     * The VDM int basic type is transcompiled to the C# int type.
     */
    @Test
    fun int_BecomesInt()
    {
        val int = transcompileSingleDocument("""
            functions
                Charlie () i: int
                post true;
            """)

        int.shouldContain(a("public static int Charlie()"))
    }

    /**
     * The VDM nat basic type is transcompiled to the C# int type.
     */
    @Test
    fun nat_BecomesInt()
    {
        val nat = transcompileSingleDocument("""
            functions
                Delta () n: nat
                post true;
            """)

        nat.shouldContain(a("public static int Delta()"))
    }

    /**
     * The VDM nat1 basic type is transcompiled to the C# int type.
     */
    @Test
    fun nat1_BecomesInt()
    {
        val nat1 = transcompileSingleDocument("""
            functions
                Echo () n: nat1
                post true;
            """)

        nat1.shouldContain(a("public static int Echo()"))
    }

    /**
     * The VDM rat basic type is transcompiled to the C# double type.
     */
    @Test
    fun rat_BecomesDouble()
    {
        val rat = transcompileSingleDocument("""
            functions
                Foxtrot () r: rat
                post true;
            """)

        rat.shouldContain(a("public static double Foxtrot()"))
    }

    /**
     * The VDM real basic type is transcompiled to the C# double type.
     */
    @Test
    fun real_BecomesDouble()
    {
        val real = transcompileSingleDocument("""
            functions
                Golf () r: real
                post true;
            """)

        real.shouldContain(a("public static double Golf()"))
    }

    /**
     * The VDM token basic type is transcompiled to the C# object type.
     */
    @Test
    fun token_BecomesObject()
    {
        val token = transcompileSingleDocument("""
            functions
                Hotel () t: token
                post true;
            """)

        token.shouldContain(a("public static object Hotel()"))
    }
}
