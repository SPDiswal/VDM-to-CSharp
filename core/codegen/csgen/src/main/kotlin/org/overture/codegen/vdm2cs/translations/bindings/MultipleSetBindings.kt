package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.patterns.ASetMultipleBindIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

object MultipleSetBindings : MultipleBindingTranslationRule<ASetMultipleBindIR>
{
    override fun translate(irNode: ASetMultipleBindIR) = let {
        val patterns = irNode.patterns.map { it.inline }
        val source = irNode.set.translate

        val fromClauses = patterns.map { "from $it in ${source.format}" }
        val selectClause = "select new { ${patterns.joinToString(", ")} }"

        parseQueryExpression((fromClauses + selectClause).joinToString("\n"))
    }
}
