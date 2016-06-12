package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.ASkipStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.CsBlockStatement

object SkipStatements : StatementTranslationRule<ASkipStmIR>
{
    override fun translate(irNode: ASkipStmIR)
        = CsBlockStatement(emptyList())
}
