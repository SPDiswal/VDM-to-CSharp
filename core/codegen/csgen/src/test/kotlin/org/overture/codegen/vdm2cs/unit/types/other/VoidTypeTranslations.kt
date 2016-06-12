package org.overture.codegen.vdm2cs.unit.types.other

import org.overture.codegen.ir.types.AVoidTypeIR
import org.overture.codegen.vdm2cs.translations.types.other.VoidTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.voidType

class VoidTypeTranslations : TypeTranslationRuleSpek<AVoidTypeIR>(VoidTypes)
{
    init
    {
        "void" describesThat
            voidType becomes "void"
    }
}
