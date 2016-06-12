package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.AIntNumericBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object IntTypes : TypeTranslationRule<AIntNumericBasicTypeIR>
{
    override fun translate(irNode: AIntNumericBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("int?")
        else               -> parseType("int")
    }
}
