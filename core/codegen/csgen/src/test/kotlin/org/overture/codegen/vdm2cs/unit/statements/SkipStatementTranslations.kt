package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.*
import org.overture.codegen.vdm2cs.translations.statements.*
import org.overture.codegen.vdm2cs.utilities.*

class SkipStatementTranslations : StatementTranslationRuleSpek<ASkipStmIR>(SkipStatements)
{
    init
    {
        "skip" describesThat
            skipStatement becomes
            "{ }"
    }
}
