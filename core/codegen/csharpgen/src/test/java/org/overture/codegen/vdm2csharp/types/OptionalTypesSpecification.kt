package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class OptionalTypesSpecification : TranscompilerSpecification()
{
    /**
     * The VDM optional type of bool is transcompiled to the C# bool? (nullable bool) type.
     */
    @Test
    fun optionalBool_BecomesNullableBool()
    {
        val optionalBool = transcompileSingleDocument("""
            functions
                Alpha () b: [bool]
                post true;
            """)

        optionalBool.shouldContain(a("public static bool? Alpha()"))
    }

    /**
     * The VDM optional type of char is transcompiled to the C# char? (nullable char) type.
     */
    @Test
    fun optionalChar_BecomesNullableChar()
    {
        val optionalChar = transcompileSingleDocument("""
            functions
                Bravo () c: [char]
                post true;
            """)

        optionalChar.shouldContain(a("public static char? Bravo()"))
    }

    /**
     * The VDM optional type of int is transcompiled to the C# int? (nullable int) type.
     */
    @Test
    fun optionalInt_BecomesNullableInt()
    {
        val optionalInt = transcompileSingleDocument("""
            functions
                Charlie () i: [int]
                post true;
            """)

        optionalInt.shouldContain(a("public static int? Charlie()"))
    }

    /**
     * The VDM optional type of nat is transcompiled to the C# int? (nullable int) type.
     */
    @Test
    fun optionalNat_BecomesNullableInt()
    {
        val optionalNat = transcompileSingleDocument("""
            functions
                Delta () n: [nat]
                post true;
            """)

        optionalNat.shouldContain(a("public static int? Delta()"))
    }

    /**
     * The VDM optional type of nat1 is transcompiled to the C# int? (nullable int) type.
     */
    @Test
    fun optionalNat1_BecomesNullableInt()
    {
        val optionalNat1 = transcompileSingleDocument("""
            functions
                Echo () n: [nat1]
                post true;
            """)

        optionalNat1.shouldContain(a("public static int? Echo()"))
    }

    /**
     * The VDM optional type of rat is transcompiled to the C# double? (nullable double) type.
     */
    @Test
    fun optionalRat_BecomesNullableDouble()
    {
        val optionalRat = transcompileSingleDocument("""
            functions
                Foxtrot () r: [rat]
                post true;
            """)

        optionalRat.shouldContain(a("public static double? Foxtrot()"))
    }

    /**
     * The VDM optional type of real is transcompiled to the C# double? (nullable double) type.
     */
    @Test
    fun optionalReal_BecomesNullableDouble()
    {
        val optionalReal = transcompileSingleDocument("""
            functions
                Golf () r: [real]
                post true;
            """)

        optionalReal.shouldContain(a("public static double? Golf()"))
    }

    /**
     * The VDM optional type of token is transcompiled to the C# object type.
     */
    @Test
    fun optionalToken_BecomesObject()
    {
        val optionalToken = transcompileSingleDocument("""
            functions
                Hotel () t: [token]
                post true;
            """)

        optionalToken.shouldContain(a("public static object Hotel()"))
    }

    /**
     * The VDM optional type of seq of char is transcompiled to the C# string type.
     */
    @Test
    fun optionalSeqOfChar_BecomesString()
    {
        val optionalSeqOfChar = transcompileSingleDocument("""
            functions
                India () s: [seq of char]
                post true;
            """)

        optionalSeqOfChar.shouldContain(a("public static string India()"))
    }

    /**
     * The VDM optional type of seq of optional int is transcompiled to the C# IList<int?> type.
     */
    @Test
    fun optionalSeqOfOptionalInt_BecomesIListNullableInt()
    {
        val optionalSeqOfOptionalInt = transcompileSingleDocument("""
            functions
                Juliet () s: [seq of [int]]
                post true;
            """)

        optionalSeqOfOptionalInt.shouldContain(a("public static IList<int?> Juliet()"))
    }
}
