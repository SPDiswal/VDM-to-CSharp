package org.overture.codegen.vdm2cs.translations.expressions.`is`

import org.overture.codegen.ir.expressions.ATupleIsExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

object IsTuples : ExpressionTranslationRule<ATupleIsExpIR>
{
    override fun translate(irNode: ATupleIsExpIR): CsExpression
    {
        val type = irNode.checkedType
        val expression = irNode.exp.inline
        val invariant = TypeInvariants.generateInvariant(type, "_") ?: "_ != null"

        return parseExpression("Let(() => { var _ = $expression as ${type.inline};\nreturn $invariant; })")
    }
}
