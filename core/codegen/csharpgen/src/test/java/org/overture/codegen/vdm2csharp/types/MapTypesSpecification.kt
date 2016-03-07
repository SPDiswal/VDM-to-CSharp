package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class MapTypesSpecification : TranscompilerSpecification()
{
    /**
     * The VDM map char to bool type is transcompiled
     * to the C# IDictionary<char, bool> type.
     */
    @Test
    fun mapCharToBool_BecomesIDictionaryCharBool()
    {
        val mapCharToBool = transcompileSingleDocument("""
            functions
                Alpha () m: map char to bool
                post true;
            """)

        mapCharToBool.shouldContain(a("public static IDictionary<char, bool> Alpha()"))
    }

    /**
     * The VDM map int to int type is transcompiled
     * to the C# IDictionary<int, int> type.
     */
    @Test
    fun mapIntToInt_BecomesIDictionaryIntInt()
    {
        val mapIntToInt = transcompileSingleDocument("""
            functions
                Bravo () m: map int to int
                post true;
            """)

        mapIntToInt.shouldContain(a("public static IDictionary<int, int> Bravo()"))
    }

    /**
     * The VDM map real to map int to int type is transcompiled
     * to the C# IDictionary<double, IDictionary<int, int>> type.
     */
    @Test
    fun mapRealToMapIntToInt_BecomesIDictionaryDoubleIDictionaryIntInt()
    {
        val mapRealToMapIntToInt = transcompileSingleDocument("""
            functions
                Charlie () m: map real to map int to int
                post true;
            """)

        mapRealToMapIntToInt.shouldContain(a("public static IDictionary<double, IDictionary<int, int>> Charlie()"))
    }

    /**
     * The VDM inmap char to bool type is transcompiled
     * to the C# IDictionary<char, bool> type.
     */
    @Test
    fun inmapCharToBool_BecomesIDictionaryCharBool()
    {
        val inmapCharToBool = transcompileSingleDocument("""
            functions
                Delta () m: inmap char to bool
                post true;
            """)

        inmapCharToBool.shouldContain(a("public static IDictionary<char, bool> Delta()"))
    }

    /**
     * The VDM inmap int to int type is transcompiled
     * to the C# IDictionary<int, int> type.
     */
    @Test
    fun inmapIntToInt_BecomesIDictionaryIntInt()
    {
        val inmapIntToInt = transcompileSingleDocument("""
            functions
                Echo () m: inmap int to int
                post true;
            """)

        inmapIntToInt.shouldContain(a("public static IDictionary<int, int> Echo()"))
    }

    /**
     * The VDM inmap real to map int to int type is transcompiled
     * to the C# IDictionary<double, IDictionary<int, int>> type.
     */
    @Test
    fun inmapRealToMapIntToInt_BecomesIDictionaryDoubleIDictionaryIntInt()
    {
        val inmapRealToMapIntToInt = transcompileSingleDocument("""
            functions
                Foxtrot () m: inmap real to map int to int
                post true;
            """)

        inmapRealToMapIntToInt.shouldContain(a("public static IDictionary<double, IDictionary<int, int>> Foxtrot()"))
    }
}
