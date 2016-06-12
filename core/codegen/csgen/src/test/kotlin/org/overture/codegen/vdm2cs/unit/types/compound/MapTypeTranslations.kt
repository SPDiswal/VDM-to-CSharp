package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.SMapTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.MapTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class MapTypeTranslations : TypeTranslationRuleSpek<SMapTypeIR>(MapTypes)
{
    init
    {
        "map bool to bool" describesThat
            map(boolType to boolType) becomes
            "Dictionary<bool, bool>"

        "map nat to nat1" describesThat
            map(natType to nat1Type) becomes
            "Dictionary<int, int>"

        "map bool to map bool to bool" describesThat
            map(boolType to map(boolType to boolType)) becomes
            "Dictionary<bool, Dictionary<bool, bool>>"

        "map nat to map nat to seq of char" describesThat
            map(natType to map(natType to seqOf(charType))) becomes
            "Dictionary<int, Dictionary<int, string>>"

        "inmap bool to bool" describesThat
            inmap(boolType to boolType) becomes
            "Dictionary<bool, bool>"

        "inmap nat to nat1" describesThat
            inmap(natType to nat1Type) becomes
            "Dictionary<int, int>"

        "inmap bool to inmap bool to bool" describesThat
            inmap(boolType to inmap(boolType to boolType)) becomes
            "Dictionary<bool, Dictionary<bool, bool>>"

        "inmap nat to inmap nat to seq of char" describesThat
            inmap(natType to inmap(natType to seqOf(charType))) becomes
            "Dictionary<int, Dictionary<int, string>>"
    }
}
