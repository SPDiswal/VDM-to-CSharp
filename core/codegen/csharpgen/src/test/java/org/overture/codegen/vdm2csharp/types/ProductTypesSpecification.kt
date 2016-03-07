package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class ProductTypesSpecification : TranscompilerSpecification()
{
    // TODO: Support tuples with more than seven items (the C# Tuple type only supports up to seven items by default).

    /**
     * The VDM nat * nat product type is transcompiled
     * to the C# Tuple<int, int> type.
     */
    @Test
    fun natNatProduct_BecomesTupleIntInt()
    {
        val natNatProduct = transcompileSingleDocument("""
            functions
                Alpha () p: nat * nat
                post true;
            """)

        natNatProduct.shouldContain(a("public static Tuple<int, int> Alpha()"))
    }

    /**
     * The VDM bool * char * int product type is transcompiled
     * to the C# Tuple<bool, char, int> type.
     */
    @Test
    fun boolCharIntProduct_BecomesTupleBoolCharInt()
    {
        val boolCharIntProduct = transcompileSingleDocument("""
            functions
                Bravo () p: bool * char * int
                post true;
            """)

        boolCharIntProduct.shouldContain(a("public static Tuple<bool, char, int> Bravo()"))
    }

    /**
     * The VDM seq of char * set of int * nat1 * rat * real product type is transcompiled
     * to the C# Tuple<string, ISet<int>, int, double, double> type.
     */
    @Test
    fun seqOfCharSetOfIntNat1RatRealProduct_BecomesTupleStringISetIntIntDoubleDouble()
    {
        val seqOfCharSetOfIntNat1RatRealProduct = transcompileSingleDocument("""
            functions
                Charlie () p: seq of char * set of int * nat1 * rat * real
                post true;
            """)

        seqOfCharSetOfIntNat1RatRealProduct.shouldContain(
            a("public static Tuple<string, ISet<int>, int, double, double> Charlie()"))
    }
}
