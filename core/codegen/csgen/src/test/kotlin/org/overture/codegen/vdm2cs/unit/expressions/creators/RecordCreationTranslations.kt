package org.overture.codegen.vdm2cs.unit.expressions.creators

import org.overture.codegen.ir.expressions.ANewExpIR
import org.overture.codegen.vdm2cs.translations.expressions.creators.RecordCreations
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class RecordCreationTranslations : ExpressionTranslationRuleSpek<ANewExpIR>(RecordCreations)
{
    init
    {
        "mk_$nextPlaceholder(1, 2)" describesThat
            mkRecord(name = typeName(name = placeholder),
                     fields = listOf(1.toLiteral, 2.toLiteral)) becomes
            "new $placeholder(1, 2)"

        "mk_$nextLowerCasePlaceholder(true, 42, \"ABC\")" describesThat
            mkRecord(name = typeName(name = lowerCasePlaceholder),
                     fields = listOf(true.toLiteral, 42.toLiteral, "ABC".toLiteral)) becomes
            "new $placeholder(true, 42, \"ABC\")"

        "mk_External`$nextPlaceholder()" describesThat
            mkRecord(name = typeName(qualifier = "External",
                                     name = placeholder),
                     fields = emptyList()) becomes
            "new External.$placeholder()"
    }
}
