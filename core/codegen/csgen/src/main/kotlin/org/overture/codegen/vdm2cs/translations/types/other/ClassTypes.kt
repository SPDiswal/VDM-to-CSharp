package org.overture.codegen.vdm2cs.translations.types.other

import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object ClassTypes : TypeTranslationRule<AClassTypeIR>
{
    override fun translate(irNode: AClassTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType(irNode.name)
    }
}
