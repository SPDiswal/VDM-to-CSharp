package org.overture.codegen.vdm2cs.translations.expressions.scope

import org.overture.codegen.ir.expressions.ALetBeStExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object LetBeSuchThatExpressions : ExpressionTranslationRule<ALetBeStExpIR>
{
    override fun translate(irNode: ALetBeStExpIR) = let {
        val binding = irNode.header.binding
        val predicate = irNode.header.suchThat

        val letBindings = binding.translateWithPredicate(predicate).let { "var _ = (${it.format}).First();" }
        val patterns = binding.patterns.map {
            val identifier = it.inline
            "var $identifier = _.$identifier;"
        }.joinToString("\n")

        parseExpression("""
        Let(() =>
        {
            $letBindings
            {
                $patterns
                return ${irNode.value.inline};
            }
        })"""
        )
    }
}
