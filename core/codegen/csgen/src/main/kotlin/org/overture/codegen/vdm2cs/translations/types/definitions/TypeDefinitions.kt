package org.overture.codegen.vdm2cs.translations.types.definitions

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException

object TypeDefinitions : TypeDefinitionTranslationRule<ATypeDeclIR>
{
    override fun translate(irNode: ATypeDeclIR) = when (irNode.decl)
    {
        is ANamedTypeDeclIR -> NamedTypeDefinitions.translate(irNode)
        is ARecordDeclIR    -> CompositeTypeDefinitions.translate(irNode)
        else                -> throw UnsupportedTranslationException(irNode.decl)
    }
}
