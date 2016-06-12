package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.CsBlockStatement
import org.overture.codegen.vdm2cs.utilities.*

object BlockStatements : StatementTranslationRule<ABlockStmIR>
{
    override fun translate(irNode: ABlockStmIR): CsBlockStatement
    {
        val localVariables = irNode.localDefs.map {
            "var ${it.pattern.inline} = ${it.exp.inline};"
        }.joinToString("\n", prefix = "\n", postfix = "\n")

        return parseBlock(
            "{" +
            localVariables +
            irNode.statements.map { it.inline }.joinToString("\n") +
            "}"
        )
    }
}
