package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class SeqTypesSpecification : TranscompilerSpecification()
{
    /**
     * The VDM seq of char type is transcompiled to the C# string type.
     */
    @Test
    fun seqOfChar_BecomesString()
    {
        val seqOfChar = transcompileSingleDocument("""
            functions
                Alpha () s: seq of char
                post true;
            """)

        seqOfChar.shouldContain(a("public static string Alpha()"))
    }

    /**
     * The VDM seq of int type is transcompiled to the C# IList<int> type.
     */
    @Test
    fun seqOfInt_BecomesIListInt()
    {
        val seqOfInt = transcompileSingleDocument("""
            functions
                Bravo () s: seq of int
                post true;
            """)

        seqOfInt.shouldContain(a("public static IList<int> Bravo()"))
    }

    /**
     * The VDM seq of seq of real type is transcompiled to the C# IList<IList<double>> type.
     */
    @Test
    fun seqOfSeqOfReal_BecomesIListIListDouble()
    {
        val seqOfSeqOfReal = transcompileSingleDocument("""
            functions
                Charlie () s: seq of seq of real
                post true;
            """)

        seqOfSeqOfReal.shouldContain(a("public static IList<IList<double>> Charlie()"))
    }

    /**
     * The VDM seq1 of char type is transcompiled to the C# string type.
     */
    @Test
    fun seq1OfChar_BecomesString()
    {
        val seq1OfChar = transcompileSingleDocument("""
            functions
                Delta () s: seq1 of char
                post true;
            """)

        seq1OfChar.shouldContain(a("public static string Delta()"))
    }

    /**
     * The VDM seq1 of int type is transcompiled to the C# IList<int> type.
     */
    @Test
    fun seq1OfInt_BecomesIListInt()
    {
        val seq1OfInt = transcompileSingleDocument("""
            functions
                Echo () s: seq1 of int
                post true;
            """)

        seq1OfInt.shouldContain(a("public static IList<int> Echo()"))
    }

    /**
     * The VDM seq1 of seq of real type is transcompiled to the C# IList<IList<double>> type.
     */
    @Test
    fun seq1OfSeqOfReal_BecomesIListIListDouble()
    {
        val seq1OfSeqOfReal = transcompileSingleDocument("""
            functions
                Foxtrot () s: seq1 of seq of real
                post true;
            """)

        seq1OfSeqOfReal.shouldContain(a("public static IList<IList<double>> Foxtrot()"))
    }
}
