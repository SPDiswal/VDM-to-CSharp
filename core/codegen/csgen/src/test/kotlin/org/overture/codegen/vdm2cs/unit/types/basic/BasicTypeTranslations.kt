package org.overture.codegen.vdm2cs.unit.types.basic

import org.overture.codegen.ir.types.SBasicTypeIR
import org.overture.codegen.vdm2cs.translations.types.basic.BasicTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class BasicTypeTranslations : TypeTranslationRuleSpek<SBasicTypeIR>(BasicTypes)
{
    init
    {
        "bool" describesThat
            boolType becomes
            "bool"

        "char" describesThat
            charType becomes
            "char"

        "int" describesThat
            intType becomes
            "int"

        "nat" describesThat
            natType becomes
            "int"

        "nat1" describesThat
            nat1Type becomes
            "int"

        "rat" describesThat
            ratType becomes
            "decimal"

        "real" describesThat
            realType becomes
            "decimal"

        "token" describesThat
            tokenType becomes
            "Token"
    }
}
