package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ABoolBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object BoolTypes : TypeTranslationRule<ABoolBasicTypeIR>
{
    override fun translate(irNode: ABoolBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("bool?")
        else               -> parseType("bool")
    }
}
