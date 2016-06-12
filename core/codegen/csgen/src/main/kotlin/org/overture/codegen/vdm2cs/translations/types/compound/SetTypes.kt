package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.ASetSetTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SetTypes : TypeTranslationRule<ASetSetTypeIR>
{
    override fun translate(irNode: ASetSetTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType("HashSet<${irNode.setOf.inline}>")
    }
}
