package org.overture.codegen.vdm2cs.unit.types.other

import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.translations.types.compound.*
import org.overture.codegen.vdm2cs.translations.types.other.StringTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class StringTypeTranslations : TypeTranslationRuleSpek<AStringTypeIR>(StringTypes)
{
    init
    {
        "string" describesThat
            stringType becomes "string"
    }
}
