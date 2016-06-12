package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.AUnionTypeIR
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object UnionTypes : TypeTranslationRule<AUnionTypeIR>
{
    override fun translate(irNode: AUnionTypeIR) = when
    {
        irNode.isNamedType                -> parseType(irNode.promoteNamedType)
        irNode.isNonOptionalUnionOfQuotes -> parseType("Quote")
        irNode.isOptionalUnionOfQuotes    -> parseType("Quote?")
        else                              -> parseType("object")
    }
}
