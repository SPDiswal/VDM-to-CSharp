package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.AMethodTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object FunctionTypes : TypeTranslationRule<AMethodTypeIR>
{
    override fun translate(irNode: AMethodTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType("Func<${(irNode.params + irNode.result).joinTypes}>")
    }
}
