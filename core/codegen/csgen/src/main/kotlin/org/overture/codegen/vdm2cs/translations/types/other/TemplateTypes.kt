package org.overture.codegen.vdm2cs.translations.types.other

import org.overture.codegen.ir.types.ATemplateTypeIR
import org.overture.codegen.vdm2cs.utilities.parseType
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

object TemplateTypes : TypeTranslationRule<ATemplateTypeIR>
{
    override fun translate(irNode: ATemplateTypeIR)
        = parseType(irNode.name.normalisedTypeParameterName)

    private val String.normalisedTypeParameterName: String
        get() = this.ensurePrefix("T")

    private fun String.ensurePrefix(prefix: String) = when
    {
        this.length < 2 && this == prefix                                    -> this
        this.length >= 2 && this.startsWith(prefix) && this[1].isUpperCase() -> this
        else                                                                 -> "$prefix${this.toUpperCamelCase()}"
    }
}
