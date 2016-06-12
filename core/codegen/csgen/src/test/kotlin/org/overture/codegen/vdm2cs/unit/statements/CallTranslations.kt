package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.APlainCallStmIR
import org.overture.codegen.vdm2cs.translations.statements.Calls
import org.overture.codegen.vdm2cs.utilities.*

class CallTranslations : StatementTranslationRuleSpek<APlainCallStmIR>(Calls)
{
    init
    {
        "$nextPlaceholder()" describesThat
            call(name = placeholder, resultType = boolType,
                 arguments = emptyList()) becomes
            "$placeholder();"

        "$nextLowerCasePlaceholder(1)" describesThat
            call(name = lowerCasePlaceholder, resultType = intType,
                 arguments = listOf(1.toLiteral)) becomes
            "$placeholder(1);"

        "$nextPlaceholder(\"Hello, World!\", 2)" describesThat
            call(name = placeholder, resultType = seqOf(charType),
                 arguments = listOf("Hello, World!".toLiteral, 2.toLiteral)) becomes
            "$placeholder(\"Hello, World!\", 2);"

        "Outer(Inner())" describesThat
            call(name = "Outer", resultType = intType,
                 arguments = listOf((identifier("Inner", methodType(emptyList(), nat1Type))).applyWith())) becomes
            "Outer(Inner());"

        "FirstQualifier`$nextPlaceholder()" describesThat
            classType("FirstQualifier").callWith(name = placeholder, resultType = boolType,
                                                 arguments = emptyList()) becomes
            "FirstQualifier.$placeholder();"

        "SecondQualifier`$nextLowerCasePlaceholder(3, true)" describesThat
            classType("SecondQualifier").callWith(name = placeholder, resultType = intType,
                                                  arguments = listOf(3.toLiteral, true.toLiteral)) becomes
            "SecondQualifier.$placeholder(3, true);"

        "IO`println(\"ABC\")" describesThat
            classType("IO").callWith(name = "println", resultType = voidType,
                                     arguments = listOf("ABC".toLiteral)) becomes
            "Console.WriteLine(\"ABC\");"
    }
}
