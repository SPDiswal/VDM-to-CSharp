package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.ARecordTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object CompositeTypes : TypeTranslationRule<ARecordTypeIR>
{
    override fun translate(irNode: ARecordTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType(irNode.name.formatTypeName.toUpperCamelCase())
    }
}
