package org.overture.codegen.vdm2cs.translations.expressions.creators

import org.overture.codegen.ir.expressions.ATupleExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object TupleCreations : ExpressionTranslationRule<ATupleExpIR>
{
    override fun translate(irNode: ATupleExpIR)
        = parseExpression("Tuple.Create(${irNode.args.joinExpressions})")
}
