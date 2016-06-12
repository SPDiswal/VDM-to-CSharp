package org.overture.codegen.vdm2cs.translations.functionality.exceptions

import org.overture.codegen.ir.PIR
import org.overture.codegen.ir.statements.AErrorStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.parseStatement

object ErrorStatements : TranslationRule<PIR, CsStatement>
{
    override fun translate(irNode: PIR) = when (irNode)
    {
        is AErrorStmIR -> parseStatement("throw new Exception();")
        else           ->
            throw IllegalStateException(irNode.javaClass.simpleName + " does not become an exception in C#.")
    }
}
