package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.ASetSetTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.SetTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SetTypeTranslations : TypeTranslationRuleSpek<ASetSetTypeIR>(SetTypes)
{
    init
    {
        "set of char" describesThat
            setOf(charType) becomes
            "HashSet<char>"

        "set of nat" describesThat
            setOf(natType) becomes
            "HashSet<int>"

        "set of set of char" describesThat
            setOf(setOf(charType)) becomes
            "HashSet<HashSet<char>>"

        "set of set of nat" describesThat
            setOf(setOf(natType)) becomes
            "HashSet<HashSet<int>>"
    }
}
