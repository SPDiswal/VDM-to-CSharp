package org.overture.codegen.vdm2cs.translations.bindings

import org.overture.codegen.ir.patterns.ASetBindIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

object SingleSetBindings : SingleBindingTranslationRule<ASetBindIR>
{
    override fun translate(irNode: ASetBindIR) = let {
        val pattern = irNode.pattern.inline
        val source = irNode.set.translate

        val fromClause = "from $pattern in ${source.format}"
        val selectClause = "select new { $pattern }"

        parseQueryExpression(listOf(fromClause, selectClause).joinToString("\n"))
    }
}
