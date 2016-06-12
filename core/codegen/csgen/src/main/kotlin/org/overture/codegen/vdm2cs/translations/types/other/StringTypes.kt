package org.overture.codegen.vdm2cs.translations.types.other

import org.overture.codegen.ir.types.AStringTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object StringTypes : TypeTranslationRule<AStringTypeIR>
{
    override fun translate(irNode: AStringTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType("string")
    }
}
