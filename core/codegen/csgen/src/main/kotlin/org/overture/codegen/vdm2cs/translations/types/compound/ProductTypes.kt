package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.ATupleTypeIR
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object ProductTypes : TypeTranslationRule<ATupleTypeIR>
{
    // TODO Support tuples with more than seven items (the C# Tuple type only supports up to seven items by default).

    override fun translate(irNode: ATupleTypeIR) = when
    {
        irNode.isNamedType     -> parseType(irNode.promoteNamedType)
        irNode.types.size <= 7 -> parseType("Tuple<${irNode.types.joinTypes}>")
        else                   -> throw UnsupportedTranslationException(
            "There is no translation for tuples with more than seven items available yet.")
    }
}
