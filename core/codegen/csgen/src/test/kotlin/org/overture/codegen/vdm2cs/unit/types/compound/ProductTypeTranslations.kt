package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.ATupleTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.ProductTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class ProductTypeTranslations : TypeTranslationRuleSpek<ATupleTypeIR>(ProductTypes)
{
    init
    {
        "nat * nat" describesThat
            tupleOf(natType, natType) becomes
            "Tuple<int, int>"

        "bool * char * int" describesThat
            tupleOf(boolType, charType, intType) becomes
            "Tuple<bool, char, int>"

        "seq of char * set of nat * rat * nat1 * real" describesThat
            tupleOf(seqOf(charType), setOf(natType), ratType, nat1Type, realType) becomes
            "Tuple<string, HashSet<int>, decimal, int, decimal>"

        "(nat * nat) * (real * seq of char)" describesThat
            tupleOf(tupleOf(natType, natType), tupleOf(realType, seqOf(charType))) becomes
            "Tuple<Tuple<int, int>, Tuple<decimal, string>>"

        "nat * nat" describesThat
            tupleOf(natType, natType, natType, natType, natType, natType, natType, natType) throws
            "There is no translation for tuples with more than seven items available yet."
    }
}
