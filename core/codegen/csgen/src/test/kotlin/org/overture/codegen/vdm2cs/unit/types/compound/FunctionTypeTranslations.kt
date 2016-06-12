package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.AMethodTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.FunctionTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class FunctionTypeTranslations : TypeTranslationRuleSpek<AMethodTypeIR>(FunctionTypes)
{
    init
    {
        "() -> bool" describesThat
            methodType(emptyList(), boolType) becomes
            "Func<bool>"

        "() -> int" describesThat
            methodType(emptyList(), intType) becomes
            "Func<int>"

        "bool -> bool" describesThat
            methodType(listOf(boolType), boolType) becomes
            "Func<bool, bool>"

        "bool -> int" describesThat
            methodType(listOf(boolType), intType) becomes
            "Func<bool, int>"

        "bool * int -> int" describesThat
            methodType(listOf(boolType, intType), intType) becomes
            "Func<bool, int, int>"

        "char * char * char -> seq of char" describesThat
            methodType(listOf(charType, charType, charType), seqOf(charType)) becomes
            "Func<char, char, char, string>"
    }
}
