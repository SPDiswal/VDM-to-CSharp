package org.overture.codegen.vdm2cs.translations.expressions.collections.sequences

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object SequenceComprehensions : ExpressionTranslationRule<ACompSeqExpIR>
{
    override fun translate(irNode: ACompSeqExpIR) = let {
        val binding = irNode.setBind.translate.fromSources.first()
        val (rangeVariable, source) = binding

        val fromClause = "from ${rangeVariable.format} in ${source.format}"
        val whereClause = irNode.predicate?.let { "where ${it.inline}" }
        val selectClause = irNode.first.let { "select ${it.inline}" }

        parseExpression(
            "(${listOf(fromClause, whereClause, selectClause).filterNotNull().joinToString("\n")}).ToList()"
        )
    }
}
