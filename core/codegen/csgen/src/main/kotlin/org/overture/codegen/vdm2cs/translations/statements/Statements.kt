package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.SStmIR
import org.overture.codegen.ir.statements.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.functionality.exceptions.*

object Statements : StatementTranslationRule<SStmIR>
{
    override fun translate(irNode: SStmIR): CsStatement = when (irNode)
    {
        is AAssignmentStmIR     -> Assignments.translate(irNode)
        is AAtomicStmIR         -> AtomicStatements.translate(irNode)
        is ABlockStmIR          -> BlockStatements.translate(irNode)
        is ASkipStmIR           -> SkipStatements.translate(irNode)
        is ALetBeStStmIR        -> LetBeSuchThatStatements.translate(irNode)
        is APlainCallStmIR      -> Calls.translate(irNode)
        is AIfStmIR             -> IfStatements.translate(irNode)
        is AReturnStmIR         -> ReturnStatements.translate(irNode)
        is AErrorStmIR          -> ErrorStatements.translate(irNode)
        is ANotImplementedStmIR -> IsNotYetSpecified.translate(irNode)
        else                    -> throw UnsupportedTranslationException(irNode)
    }
}
