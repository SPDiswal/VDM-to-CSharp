package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.ARecordTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.CompositeTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.recordType

class CompositeTypeTranslations : TypeTranslationRuleSpek<ARecordTypeIR>(CompositeTypes)
{
    init
    {
        "$nextPlaceholder" describesThat
            recordType(name = placeholder) becomes
            placeholder

        "$nextLowerCasePlaceholder" describesThat
            recordType(name = lowerCasePlaceholder) becomes
            placeholder

        "FirstQualifier`$nextPlaceholder = bool" describesThat
            recordType(qualifier = "FirstQualifier", name = placeholder) becomes
            "FirstQualifier.$placeholder"

        "secondQualifier`$nextLowerCasePlaceholder = char" describesThat
            recordType(qualifier = "secondQualifier", name = lowerCasePlaceholder) becomes
            "SecondQualifier.$placeholder"
    }
}
