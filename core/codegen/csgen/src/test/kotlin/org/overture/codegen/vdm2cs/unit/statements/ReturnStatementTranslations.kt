package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.AReturnStmIR
import org.overture.codegen.vdm2cs.translations.statements.ReturnStatements
import org.overture.codegen.vdm2cs.utilities.*

class ReturnStatementTranslations : StatementTranslationRuleSpek<AReturnStmIR>(ReturnStatements)
{
    init
    {
        "return true" describesThat
            returnStatement(true.toLiteral) becomes
            "return true;"

        "return 1 + 1;" describesThat
            returnStatement(1.toLiteral plus 1.toLiteral) becomes
            "return 1 + 1;"
    }
}
