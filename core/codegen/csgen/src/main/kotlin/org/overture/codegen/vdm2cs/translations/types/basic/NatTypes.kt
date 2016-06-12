package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ANatNumericBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object NatTypes : TypeTranslationRule<ANatNumericBasicTypeIR>
{
    override fun translate(irNode: ANatNumericBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("int?")
        else               -> parseType("int")
    }
}
