package org.overture.codegen.vdm2csharp.declarations

import org.junit.Test
import org.overture.codegen.vdm2csharp.utilities.*

final class ImplicitFunctionsSpecification : TranscompilerSpecification()
{
    private val alpha: String
        get() = transcompileSingleDocument("""
                functions
                    Alpha () n: nat
                    post true;
                """)

    private val bravo: String
        get() = transcompileSingleDocument("""
                functions
                    Bravo (a: int) r: real
                    pre true
                    post false;
                """)

    private val charlieDelta: String
        get() = transcompileSingleDocument("""
                functions
                    Charlie (x: nat1, y: rat, z: char) t: token
                    pre false
                    post 1 + 1 = 2;

                    Delta (foo: seq of char) bar: seq of char
                    post foo = bar;
                """)

    //region Using statements

    /**
     * The transcompiled Alpha specification contains using statements.
     */
    @Test
    fun alpha_HasUsingStatements()
    {
        alpha.shouldContain(
            a("""
            using System;
            using System.Diagnostics.Contracts;
            """))
    }

    /**
     * The transcompiled Bravo specification contains using statements.
     */
    @Test
    fun bravo_HasUsingStatements()
    {
        bravo.shouldContain(
            a("""
            using System;
            using System.Diagnostics.Contracts;
            """))
    }

    //endregion

    //region Namespaces

    /**
     * The transcompiled Alpha specification contains a namespace called 'VdmToCSharp'.
     */
    @Test
    fun alpha_HasNamespace()
    {
        alpha.shouldContain(
            a("namespace VdmToCSharp")
            {
                anything()
            })
    }

    /**
     * The transcompiled Bravo specification contains a namespace called 'VdmToCSharp'.
     */
    @Test
    fun bravo_HasNamespace()
    {
        bravo.shouldContain(
            a("namespace VdmToCSharp")
            {
                anything()
            })
    }

    //endregion

    //region Flat specifications > Static classes

    /**
     * The flat Alpha specification is transcompiled to a static class called 'Default'.
     */
    @Test
    fun flatAlphaSpecification_BecomesStaticClass()
    {
        alpha.shouldContain(
            a("public static class Default")
            {
                anything()
            })
    }

    /**
     * The flat Bravo specification is transcompiled to a static class called 'Default'.
     */
    @Test
    fun flatBravoSpecification_BecomesStaticClass()
    {
        bravo.shouldContain(
            a("public static class Default")
            {
                anything()
            })
    }

    //endregion

    //region Implicit functions > [Pure] Static methods that throw NotImplementedException

    /**
     * The implicit Alpha function is transcompiled to a static method called 'Alpha'
     * that is tagged with the Pure attribute and throws a NotImplementedException.
     *
     * The postcondition is enabled by Contract.Ensures.
     */
    @Test
    fun implicitAlphaFunction_BecomesPureStaticMethod()
    {
        alpha.shouldContain(
            a("[Pure]", "public static int Alpha()")
            {
                a("""
                Contract.Ensures(post_Alpha(Contract.Result<int>()));
                throw new NotImplementedException();
                """)
            })
    }

    /**
     * The implicit Bravo function is transcompiled to a static method called 'Bravo'
     * that is tagged with the Pure attribute and throws a NotImplementedException.
     *
     * The precondition is enabled by Contract.Requires.
     * The postcondition is enabled by Contract.Ensures.
     */
    @Test
    fun implicitBravoFunction_BecomesPureStaticMethod()
    {
        bravo.shouldContain(
            a("[Pure]", "public static double Bravo(int a)")
            {
                a("""
                Contract.Requires(pre_Bravo(a));
                Contract.Ensures(post_Bravo(a, Contract.Result<double>()));
                throw new NotImplementedException();
                """)
            })
    }

    /**
     * The implicit Charlie function is transcompiled to a static method called 'Charlie'
     * that is tagged with the Pure attribute and throws a NotImplementedException.
     *
     * The precondition is enabled by Contract.Requires.
     * The postcondition is enabled by Contract.Ensures.
     */
    @Test
    fun implicitCharlieFunction_BecomesPureStaticMethod()
    {
        charlieDelta.shouldContain(
            a("[Pure]", "public static object Charlie(int x, double y, char z)")
            {
                a("""
                Contract.Requires(pre_Charlie(x, y, z));
                Contract.Ensures(post_Charlie(x, y, z, Contract.Result<object>()));
                throw new NotImplementedException();
                """)
            })
    }

    /**
     * The implicit Delta function is transcompiled to a static method called 'Delta'
     * that is tagged with the Pure attribute and throws a NotImplementedException.
     *
     * The postcondition is enabled by Contract.Ensures.
     */
    @Test
    fun implicitDeltaFunction_BecomesPureStaticMethod()
    {
        charlieDelta.shouldContain(
            a("[Pure]", "public static string Delta(string foo)")
            {
                a("""
                Contract.Ensures(post_Delta(foo, Contract.Result<string>()));
                throw new NotImplementedException();
                """)
            })
    }

    //endregion

    //region Code contract functions > [Pure] Static methods

    /**
     * The post_Alpha function is transcompiled to a static method called 'post_Alpha'
     * that is tagged with the Pure attribute.
     */
    @Test
    fun postAlpha_BecomesPureStaticMethod()
    {
        alpha.shouldContain(
            a("[Pure]", "public static bool post_Alpha(int n)")
            {
                a("return true;")
            })
    }

    /**
     * The pre_Bravo function is transcompiled to a static method called 'pre_Bravo'
     * that is tagged with the Pure attribute.
     */
    @Test
    fun preBravo_BecomesPureStaticMethod()
    {
        bravo.shouldContain(
            a("[Pure]", "public static bool pre_Bravo(int a)")
            {
                a("return true;")
            })
    }

    /**
     * The post_Bravo function is transcompiled to a static method called 'post_Bravo'
     * that is tagged with the Pure attribute.
     */
    @Test
    fun postBravo_BecomesPureStaticMethod()
    {
        bravo.shouldContain(
            a("[Pure]", "public static bool post_Bravo(int a, double r)")
            {
                a("return false;")
            })
    }

    /**
     * The pre_Charlie function is transcompiled to a static method called 'pre_Charlie'
     * that is tagged with the Pure attribute.
     */
    @Test
    fun preCharlie_BecomesPureStaticMethod()
    {
        charlieDelta.shouldContain(
            a("[Pure]", "public static bool pre_Charlie(int x, double y, char z)")
            {
                a("return false;")
            })
    }

    /**
     * The post_Charlie function is transcompiled to a static method called 'post_Charlie'
     * that is tagged with the Pure attribute.
     */
    @Test
    fun postCharlie_BecomesPureStaticMethod()
    {
        charlieDelta.shouldContain(
            a("[Pure]", "public static bool post_Charlie(int x, double y, char z, object t)")
            {
                a("return 1 + 1 == 2;")
            })
    }

    /**
     * The post_Delta function is transcompiled to a static method called 'post_Delta'
     * that is tagged with the Pure attribute.
     */
    @Test
    fun postDelta_BecomesPureStaticMethod()
    {
        charlieDelta.shouldContain(
            a("[Pure]", "public static bool post_Delta(string foo, string bar)")
            {
                a("return foo == bar;")
            })
    }

    //endregion
}
