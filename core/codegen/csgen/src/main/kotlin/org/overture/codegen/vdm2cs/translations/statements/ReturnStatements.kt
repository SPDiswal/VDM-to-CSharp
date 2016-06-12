package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.AReturnStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.CsReturnStatement
import org.overture.codegen.vdm2cs.utilities.translate

object ReturnStatements : StatementTranslationRule<AReturnStmIR>
{
    override fun translate(irNode: AReturnStmIR)
        = CsReturnStatement(irNode.exp.translate)
}
