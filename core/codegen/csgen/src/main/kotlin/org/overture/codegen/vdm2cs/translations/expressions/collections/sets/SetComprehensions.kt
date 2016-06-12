package org.overture.codegen.vdm2cs.translations.expressions.collections.sets

import org.overture.codegen.ir.expressions.ACompSetExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SetComprehensions : ExpressionTranslationRule<ACompSetExpIR>
{
    override fun translate(irNode: ACompSetExpIR): CsExpression
    {
        val bindings = irNode.bindings.flatMap { it.translate.fromSources }

        val fromClauses = bindings.map {
            val (rangeVariable, source) = it
            "from ${rangeVariable.format} in ${source.format}"
        }
        val whereClause = irNode.predicate?.let { "where ${it.inline}" }
        val selectClause = irNode.first.let { "select ${it.inline}" }

        return parseExpression(
            "(${(fromClauses + whereClause + selectClause).filterNotNull().joinToString("\n")}).ToHashSet()"
        )
    }
}
