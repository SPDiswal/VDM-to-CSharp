package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.APlainCallStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

object Calls : StatementTranslationRule<APlainCallStmIR>
{
    // TODO Recognise IO`printf"

    override fun translate(irNode: APlainCallStmIR): CsStatement
    {
        val qualifier = irNode.classType?.inline?.let { "$it." } ?: ""
        val name = irNode.name.toUpperCamelCase()
        val arguments = irNode.args.joinExpressions

        return when
        {
            qualifier + name == "IO.Println" -> parseStatement("Console.WriteLine($arguments);")
            qualifier + name == "IO.Printf"  -> parseStatement("Console.WriteLine($arguments);")
            else                             -> parseStatement("$qualifier$name($arguments);")
        }
    }
}
