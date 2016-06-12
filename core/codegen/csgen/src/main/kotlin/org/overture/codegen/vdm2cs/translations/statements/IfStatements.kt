package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.AIfStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.*
import org.overture.codegen.vdm2cs.utilities.*

object IfStatements : StatementTranslationRule<AIfStmIR>
{
    override fun translate(irNode: AIfStmIR): CsStatement
    {
        val ifBranch = irNode.ifExp.translate to irNode.thenStm.translateAsBlockStatement
        val elseIfBranches = irNode.elseIf.map { it.elseIf.translate to it.thenStm.translateAsBlockStatement }
        val elseStatement = irNode.elseStm?.translateAsBlockStatement
        val branches = elseIfBranches.reversed() + ifBranch

        val alignedBranches = mutableListOf<CsIfStatement>()

        for ((i, ifThen) in branches.withIndex())
        {
            val (ifCondition, thenStatement) = ifThen

            alignedBranches += CsIfStatement(ifCondition, thenStatement, when (i)
            {
                0    -> elseStatement
                else -> alignedBranches[i - 1]
            })
        }

        return alignedBranches.last()
    }
}
