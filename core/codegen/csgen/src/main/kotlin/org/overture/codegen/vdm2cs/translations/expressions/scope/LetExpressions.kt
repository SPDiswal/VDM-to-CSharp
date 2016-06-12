package org.overture.codegen.vdm2cs.translations.expressions.scope

import org.overture.codegen.ir.expressions.ALetDefExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object LetExpressions : ExpressionTranslationRule<ALetDefExpIR>
{
    override fun translate(irNode: ALetDefExpIR) = let {
        val localVariables = irNode.localDefs.map {
            "var ${it.pattern.inline} = ${it.exp.inline};"
        }.joinToString("\n", prefix = "\n", postfix = "\n")

        parseExpression(
            "Let(() => {" +
            localVariables +
            "return ${irNode.exp.inline};" +
            "})"
        )
    }
}
