package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.statements.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

object LetBeSuchThatStatements : StatementTranslationRule<ALetBeStStmIR>
{
    override fun translate(irNode: ALetBeStStmIR) = let {
        val binding = irNode.header.binding
        val predicate = irNode.header.suchThat

        val letBindings = binding.translateWithPredicate(predicate).let { "var _ = (${it.format}).First();" }
        val patterns = binding.patterns.map {
            val identifier = it.inline
            "var $identifier = _.$identifier;"
        }.joinToString("\n")

        val statement = when (irNode.statement)
        {
            is ASkipStmIR -> ""
            else          -> irNode.statement.inline
        }

        parseStatement("""
        {
            $letBindings
            {
                $patterns
                $statement
            }
        }"""
        )
    }
}
