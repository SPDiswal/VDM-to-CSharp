package org.overture.codegen.vdm2cs.translations.types.other

import org.overture.codegen.ir.types.AUnknownTypeIR
import org.overture.codegen.vdm2cs.utilities.parseType
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule

object UnknownTypes : TypeTranslationRule<AUnknownTypeIR>
{
    override fun translate(irNode: AUnknownTypeIR) = parseType("object")
}
