package org.overture.codegen.vdm2cs.translations.expressions.quantifiers

import org.overture.codegen.ir.expressions.SQuantifierExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

object QuantifierUtilities
{
    fun constructQuantifierQuerySyntax(irNode: SQuantifierExpIR): String
    {
        val bindings = irNode.bindList.flatMap { it.translate.fromSources }
        val predicate = irNode.predicate.inline

        val fromClauses = bindings.map {
            val (rangeVariable, source) = it
            "from ${rangeVariable.format} in ${source.format}"
        }
        val selectClause = "select $predicate"
        return (fromClauses + selectClause).joinToString("\n")
    }
}
