package org.overture.codegen.vdm2cs.translations.expressions.collections.mappings

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule

object MappingExpressions : ExpressionTranslationRule<SMapExpIR>
{
    override fun translate(irNode: SMapExpIR) = when (irNode)
    {
        is ACompMapExpIR -> MappingComprehensions.translate(irNode)
        is AEnumMapExpIR -> MappingEnumerations.translate(irNode)
        else             -> throw UnsupportedTranslationException(irNode)
    }
}
