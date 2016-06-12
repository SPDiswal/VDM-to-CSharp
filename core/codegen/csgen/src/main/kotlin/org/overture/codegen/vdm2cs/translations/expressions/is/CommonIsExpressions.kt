package org.overture.codegen.vdm2cs.translations.expressions.`is`

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.expressions.AGeneralIsExpIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

object CommonIsExpressions : ExpressionTranslationRule<AGeneralIsExpIR>
{
    override fun translate(irNode: AGeneralIsExpIR): CsExpression
    {
        val type = irNode.checkedType
        val expression = irNode.exp.inline

        return when
        {
            type is AQuoteTypeIR -> parseExpression("($expression as Quote?) == Quote.${type.value}")
            type.isStringType    -> parseExpression("$expression is string")
            type is SSetTypeIR ||
            type is SSeqTypeIR ||
            type is SMapTypeIR   -> inScope(expression, type)
            else                 -> parseExpression("$expression is ${type.inline}")
        }
    }

    private fun inScope(expression: String, type: STypeIR): CsExpression
    {
        val invariant = TypeInvariants.generateInvariant(type, "_") ?: "_ != null"
        return parseExpression("Let(() => { var _ = $expression as ${type.inline};\nreturn $invariant; })")
    }
}
