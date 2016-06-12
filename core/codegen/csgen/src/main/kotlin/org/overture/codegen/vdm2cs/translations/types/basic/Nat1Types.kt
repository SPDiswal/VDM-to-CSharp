package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ANat1NumericBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Nat1Types : TypeTranslationRule<ANat1NumericBasicTypeIR>
{
    override fun translate(irNode: ANat1NumericBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("int?")
        else               -> parseType("int")
    }
}
