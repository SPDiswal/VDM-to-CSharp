package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.AAtomicStmIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

object AtomicStatements : StatementTranslationRule<AAtomicStmIR>
{
    override fun translate(irNode: AAtomicStmIR) = parseStatement(
        "Atomic(() => {" +
        irNode.statements.map { it.inline }.joinToString("\n") +
        "});"
    )
}
