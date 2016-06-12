package org.overture.codegen.vdm2cs.translations.types.basic

import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.translations.types.TypeTranslationRule

object BasicTypes : TypeTranslationRule<SBasicTypeIR>
{
    override fun translate(irNode: SBasicTypeIR) = when (irNode)
    {
        is ABoolBasicTypeIR        -> BoolTypes.translate(irNode)
        is ACharBasicTypeIR        -> CharTypes.translate(irNode)
        is AIntNumericBasicTypeIR  -> IntTypes.translate(irNode)
        is ANatNumericBasicTypeIR  -> NatTypes.translate(irNode)
        is ANat1NumericBasicTypeIR -> Nat1Types.translate(irNode)
        is ARatNumericBasicTypeIR  -> RatTypes.translate(irNode)
        is ARealNumericBasicTypeIR -> RealTypes.translate(irNode)
        is ATokenBasicTypeIR       -> TokenTypes.translate(irNode)
        else                       -> throw UnsupportedTranslationException(irNode)
    }
}
