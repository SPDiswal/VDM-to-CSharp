package org.overture.codegen.vdm2cs.translations.types

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier
import org.overture.codegen.vdm2cs.translations.types.basic.BasicTypes
import org.overture.codegen.vdm2cs.translations.types.compound.CompoundTypes
import org.overture.codegen.vdm2cs.translations.types.other.*

object Types : TypeTranslationRule<STypeIR>
{
    override fun translate(irNode: STypeIR): CsTypeIdentifier = when (irNode)
    {
        is SBasicTypeIR    -> BasicTypes.translate(irNode)
        is AStringTypeIR   -> StringTypes.translate(irNode)
        is ATemplateTypeIR -> TemplateTypes.translate(irNode)
        is AClassTypeIR    -> ClassTypes.translate(irNode)
        is AUnknownTypeIR  -> UnknownTypes.translate(irNode)
        is AVoidTypeIR     -> VoidTypes.translate(irNode)
        else               -> CompoundTypes.translate(irNode)
    }
}
