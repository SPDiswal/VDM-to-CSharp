package org.overture.codegen.vdm2cs.unit.expressions.primary

import org.overture.codegen.ir.expressions.AApplyExpIR
import org.overture.codegen.vdm2cs.translations.expressions.primary.Applications
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ApplicationTranslations : ExpressionTranslationRuleSpek<AApplyExpIR>(Applications)
{
    init
    {
        //region FUNCTION APPLICATIONS

        "$nextPlaceholder()" describesThat
            ((identifier(placeholder, methodType(emptyList(), intType))).applyWith()) becomes
            "$placeholder()"

        "$nextLowerCasePlaceholder(1)" describesThat
            ((identifier(lowerCasePlaceholder, methodType(listOf(intType), intType))).applyWith(1.toLiteral)) becomes
            "$placeholder(1)"

        "$nextPlaceholder(\"Hello, World!\", 2)" describesThat
            ((identifier(placeholder, methodType(listOf(seqOf(charType), nat1Type), boolType)))
                .applyWith("Hello, World!".toLiteral, 2.toLiteral)) becomes
            "$placeholder(\"Hello, World!\", 2)"

        "Outer(Inner())" describesThat
            ((identifier("Outer", methodType(listOf(nat1Type), nat1Type)))
                .applyWith((identifier("Inner", methodType(emptyList(), nat1Type))).applyWith())) becomes
            "Outer(Inner())"

        "FirstQualifier`$nextPlaceholder()" describesThat
            ((qualifiedIdentifier(moduleQualifier = "FirstQualifier",
                                  name = placeholder, type = methodType(emptyList(), intType))).applyWith()) becomes
            "FirstQualifier.$placeholder()"

        "SecondQualifier`$nextLowerCasePlaceholder(3, true)" describesThat
            ((qualifiedIdentifier(moduleQualifier = "SecondQualifier",
                                  name = lowerCasePlaceholder,
                                  type = methodType(listOf(intType, boolType), seqOf(charType))))
                .applyWith(3.toLiteral, true.toLiteral)) becomes
            "SecondQualifier.$placeholder(3, true)"

        //endregion

        //region SEQUENCE APPLICATIONS

        "$nextPlaceholder(1)" describesThat
            ((identifier(placeholder, seqOf(intType))).applyWith(1.toLiteral)) becomes
            "$lowerCasePlaceholder[0]"

        "$nextLowerCasePlaceholder(a)" describesThat
            ((identifier(lowerCasePlaceholder, seqOf(intType))).applyWith(identifier("a", intType))) becomes
            "$lowerCasePlaceholder[a - 1]"

        "$nextLowerCasePlaceholder(1)" describesThat
            ((stateFieldIdentifier(lowerCasePlaceholder, seqOf(intType))).applyWith(1.toLiteral)) becomes
            "State.$placeholder[0]"

        "$nextPlaceholder(a)" describesThat
            ((stateFieldIdentifier(placeholder, seqOf(intType))).applyWith(identifier("a", intType))) becomes
            "State.$placeholder[a - 1]"

        //endregion

        //region MAPPING APPLICATIONS

        "$nextPlaceholder(1)" describesThat
            ((identifier(placeholder, map(intType to intType))).applyWith(1.toLiteral)) becomes
            "$lowerCasePlaceholder[1]"

        "$nextLowerCasePlaceholder(a)" describesThat
            ((identifier(lowerCasePlaceholder, map(intType to intType))).applyWith(
                identifier("a", intType))) becomes
            "$lowerCasePlaceholder[a]"

        "$nextLowerCasePlaceholder(1)" describesThat
            ((stateFieldIdentifier(lowerCasePlaceholder, map(intType to intType))).applyWith(
                1.toLiteral)) becomes
            "State.$placeholder[1]"

        "$nextPlaceholder(a)" describesThat
            ((stateFieldIdentifier(placeholder, map(intType to intType))).applyWith(
                identifier("a", intType))) becomes
            "State.$placeholder[a]"

        //endregion
    }
}
