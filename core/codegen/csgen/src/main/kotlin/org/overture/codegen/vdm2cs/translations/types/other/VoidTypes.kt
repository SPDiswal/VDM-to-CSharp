package org.overture.codegen.vdm2cs.translations.types.other

import org.overture.codegen.ir.types.AVoidTypeIR
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.parseType

object VoidTypes : TypeTranslationRule<AVoidTypeIR>
{
    override fun translate(irNode: AVoidTypeIR) = parseType("void")
}
