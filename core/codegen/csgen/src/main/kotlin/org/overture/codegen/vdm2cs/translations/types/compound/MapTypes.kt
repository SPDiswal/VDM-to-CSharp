package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.SMapTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object MapTypes : TypeTranslationRule<SMapTypeIR>
{
    override fun translate(irNode: SMapTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType("Dictionary<${listOf(irNode.from, irNode.to).joinTypes}>")
    }
}
