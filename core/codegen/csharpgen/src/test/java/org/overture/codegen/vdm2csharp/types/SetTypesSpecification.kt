package org.overture.codegen.vdm2csharp.types

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class SetTypesSpecification : TranscompilerSpecification()
{
    /**
     * The VDM set of char type is transcompiled to the C# ISet<char> type.
     */
    @Test
    fun setOfChar_BecomesISetChar()
    {
        val setOfChar = transcompileSingleDocument("""
            functions
                Alpha () s: set of char
                post true;
            """)

        setOfChar.shouldContain(a("public static ISet<char> Alpha()"))
    }

    /**
     * The VDM set of int type is transcompiled to the C# ISet<int> type.
     */
    @Test
    fun setOfInt_BecomesISetInt()
    {
        val setOfInt = transcompileSingleDocument("""
            functions
                Bravo () s: set of int
                post true;
            """)

        setOfInt.shouldContain(a("public static ISet<int> Bravo()"))
    }

    /**
     * The VDM set of set of real type is transcompiled to the C# ISet<ISet<double>> type.
     */
    @Test
    fun setOfSetOfReal_BecomesISetISetDouble()
    {
        val setOfSetOfReal = transcompileSingleDocument("""
            functions
                Charlie () s: set of set of real
                post true;
            """)

        setOfSetOfReal.shouldContain(a("public static ISet<ISet<double>> Charlie()"))
    }
}
