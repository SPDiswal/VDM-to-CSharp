package org.overture.codegen.vdm2cs.unit.expressions.primary

import org.overture.codegen.ir.expressions.AFieldExpIR
import org.overture.codegen.vdm2cs.translations.expressions.primary.MemberLookups
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MemberLookupTranslations : ExpressionTranslationRuleSpek<AFieldExpIR>(MemberLookups)
{
    private val someRecordType = recordType(name = "SomeRecord")

    init
    {
        "first.$nextPlaceholder()" describesThat
            (identifier("first", someRecordType) dot identifier(placeholder, intType)) becomes
            "first.$placeholder"

        "second.$nextLowerCasePlaceholder()" describesThat
            (identifier("second", someRecordType) dot identifier(lowerCasePlaceholder, intType)) becomes
            "second.$placeholder"

        "third.InBetween.$nextPlaceholder()" describesThat
            (identifier("third", someRecordType) dot
                identifier("InBetween", someRecordType) dot
                identifier(placeholder, intType)) becomes
            "third.InBetween.$placeholder"
    }
}
