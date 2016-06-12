package org.overture.codegen.vdm2cs.translations.expressions.creators

import org.overture.codegen.ir.expressions.ANewExpIR
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object RecordCreations : ExpressionTranslationRule<ANewExpIR>
{
    override fun translate(irNode: ANewExpIR)
        = parseExpression("new ${irNode.name.formatTypeName}(${irNode.args.joinExpressions})")
}
