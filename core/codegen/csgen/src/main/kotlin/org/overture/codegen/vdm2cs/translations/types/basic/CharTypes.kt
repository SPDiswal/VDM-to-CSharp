package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ACharBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object CharTypes : TypeTranslationRule<ACharBasicTypeIR>
{
    override fun translate(irNode: ACharBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("char?")
        else               -> parseType("char")
    }
}
