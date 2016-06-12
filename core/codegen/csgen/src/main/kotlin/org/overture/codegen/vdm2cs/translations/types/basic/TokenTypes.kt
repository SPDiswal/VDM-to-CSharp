package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.ATokenBasicTypeIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object TokenTypes : TypeTranslationRule<ATokenBasicTypeIR>
{
    override fun translate(irNode: ATokenBasicTypeIR) = when
    {
        irNode.isNamedType -> parseType(irNode.promoteNamedType)
        else               -> parseType("Token")
    }
}
