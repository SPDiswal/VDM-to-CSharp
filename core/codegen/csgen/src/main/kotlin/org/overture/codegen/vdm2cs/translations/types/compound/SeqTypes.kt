package org.overture.codegen.vdm2cs.translations.types.compound

import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SeqTypes : TypeTranslationRule<SSeqTypeIR>
{
    override fun translate(irNode: SSeqTypeIR) = when
    {
        irNode.isNamedType               -> parseType(irNode.promoteNamedType)
        irNode.seqOf is ACharBasicTypeIR -> parseType("string")
        else                             -> parseType("List<${irNode.seqOf.inline}>")
    }
}
