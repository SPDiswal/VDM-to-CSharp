package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ARealNumericBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object RealTypes : TypeTranslationRule<ARealNumericBasicTypeIR>
{
    override fun translate(irNode: ARealNumericBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("decimal?")
        else               -> parseType("decimal")
    }
}
