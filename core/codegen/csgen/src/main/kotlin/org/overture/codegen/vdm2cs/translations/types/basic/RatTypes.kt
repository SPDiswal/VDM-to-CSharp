package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ARatNumericBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object RatTypes : TypeTranslationRule<ARatNumericBasicTypeIR>
{
    override fun translate(irNode: ARatNumericBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        irNode.isOptional  -> parseType("decimal?")
        else               -> parseType("decimal")
    }
}
