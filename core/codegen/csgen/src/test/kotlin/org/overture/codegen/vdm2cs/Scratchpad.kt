package org.overture.codegen.vdm2cs

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.expressions.ALessNumericBinaryExpIR
import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.utils.GeneratedData
import org.overture.codegen.vdm2cs.parser.*
import org.overture.codegen.vdm2cs.translations.expressions.`is`.IsBools
import org.overture.codegen.vdm2cs.utilities.*

fun main(args: Array<String>)
{
    parseCs()
}

private fun parseVdmSl()
{
//    val result = parse("""
//        types
//            Alpha = rat
//            inv a == a >= 0;
//
//        functions
//            AlphaFunc: Alpha -> bool
//            AlphaFunc(a) == is not yet specified;
//    """)

//    val result = parse("""
//        state St of
//            a: int
//        end
//
//        operations
//            Alpha: () ==> ()
//            Alpha() == a := 1;
//    """)

//    val result = parse("""
//        functions
//            filter[@elem]: (set of @elem) * (@elem -> bool) -> (set of @elem)
//            filter(source, predicate) == is not yet specified;
//    """)

//    val result = parse("""
//        module IO
//        exports all
//
//        definitions
//
//        operations
//            println: ? ==> ()
//            println(arg) ==
//                is not yet specified;
//
//        end IO
//
//        module Main
//
//        imports from IO all
//        exports all
//
//        definitions
//
//        operations
//            test: () ==> ()
//            test() == IO`println("Hello");
//
//        end Main
//    """)

//    val result = parse("""
//        module Primary
//        imports from Secondary all
//
//        definitions
//        functions
//            Alpha: () -> bool
//            Alpha() == Secondary`Bravo();
//
//        end Primary
//
//        module Secondary
//        exports all
//
//        definitions
//        functions
//            Bravo: () -> bool
//            Bravo() == true;
//
//        end Secondary
//    """)

//    val result = parse("""
//        module Primary
//
//        definitions
//        state St of
//            x: int
//        end
//
//        operations
//            Alpha: () ==> int
//            Alpha() ==
//                let a = 5
//                in
//                (
//                    x := a;
//                    return a;
//                )
//
//        end Primary
//    """)

    val result = parse("""
        module Primary

        definitions
        types
            Alpha = nat1
            inv n == n > 10

        end Primary
    """)


    (object : CodeGenBase()
    {
        override fun genVdmToTargetLang(ir: MutableList<IRStatus<PIR>>): GeneratedData
        {
            val module = ir.map {
                IRStatus.extract(it, AModuleDeclIR::class.java).irNode
            }.first { it.name == "Primary" }

            val type = module.decls.first { it is ATypeDeclIR } as ATypeDeclIR
            println((type.inv as AFuncDeclIR).formalParams.first.type.namedInvType)

//            val operation = module.decls.first { it is AMethodDeclIR && it.name == "Alpha" } as AMethodDeclIR
//            val body = ((operation.body as ABlockStmIR)
//                .statements.first as ABlockStmIR)
//                .statements.map { it.javaClass.simpleName }

//            val operation = module.decls.first { it is ATypeDeclIR && it.decl is ANamedTypeDeclIR } as ATypeDeclIR
//            println(operation.inv)

            return GeneratedData()
        }
    }).generate(result)
}

private fun parseCs()
{
//    val result = CsParser.parse("Contract.Ensures(Contract.Result<string>() == null || !Contract.Result<string>().IsEmpty());", rule = CsParser.statement)
    //    val result = CsParser.parse("((int) x).y", rule = CsParser.expression)
    //    val result = CsParser.parse("\"Hello,\\n world!\\\"\"", rule = CsParser.stringExpression)
    //    val result = CsParser.parse("a.First.ToString()[1 + 1.First].Second", rule = CsParser.expression)
    //    val result = CsParser.parse("new Dictionary<int, object>{[1] = 1,[2] = 'H'}.Keys", rule = CsParser.expression)
    //    val result = CsParser.parse("(false ? false : false) ? (true ? true : true) : (42 ? 42 : 42)", rule = CsParser.expression)
    //    val result = CsParser.parse("from a in Alpha from b in Bravo where a + b > 4 select 2 * a", rule = CsParser.expression)
    //    val result = CsParser.parse("public enum Quotes { Accept = 1, Deny = Quotes.Accept }", rule = CsParser.enumDeclaration)
    //    val result = CsParser.parse("{" +
    //                                "    var x = 1;" +
    //                                "    var y = 2;" +
    //                                "    State.A = x;" +
    //                                "    State.B = y;" +
    //                                "}", rule = CsParser.statement)
//    val result = CsParser.parse("public static implicit operator bool(Alpha a) { }", rule = CsParser.methodDeclaration)

    val result = CsParser.parse("(x is int) ? x : false", rule = CsParser.ternaryConditionalExpression)

//    val vdm = isBool(true.toLiteral)
//    val translated = IsBools.translate(vdm)

    println(result)
//    println(translated)
//    println(result == translated)
    println()
    println(CsFormatter.format(result))
}
