package org.overture.codegen.vdm2cs.translations.expressions.collections.mappings

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object MappingComprehensions : ExpressionTranslationRule<ACompMapExpIR>
{
    override fun translate(irNode: ACompMapExpIR) = let {
        val domainType = irNode.first.left.type.inline
        val rangeType = irNode.first.right.type.inline
        val bindings = irNode.bindings.flatMap { it.translate.fromSources }

        val fromClauses = bindings.map {
            val (rangeVariable, source) = it
            "from ${rangeVariable.format} in ${source.format}"
        }
        val whereClause = irNode.predicate?.let { "where ${it.inline}" }
        val selectClause = irNode.first.let { "select new KeyValuePair<$domainType, $rangeType>(" +
                                              "${it.left.inline}, ${it.right.inline}" +
                                              ")" }

        parseExpression(
            "(${(fromClauses + whereClause + selectClause).filterNotNull().joinToString("\n")}).ToDictionary()"
        )
    }
}
