package org.overture.codegen.vdm2cs.translations.expressions.quantifiers

import org.overture.codegen.ir.expressions.AExists1QuantifierExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.utilities.parseExpression

object UniqueExistentialQuantifiers : QuantifierTranslationRule<AExists1QuantifierExpIR>
{
    override fun translate(irNode: AExists1QuantifierExpIR): CsExpression
    {
        val query = QuantifierUtilities.constructQuantifierQuerySyntax(irNode)
        return parseExpression("($query).Count(_ => _) == 1")
    }
}
