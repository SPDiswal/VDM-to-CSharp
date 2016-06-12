package org.overture.codegen.vdm2cs.translations.expressions.quantifiers

import org.overture.codegen.ir.expressions.AExistsQuantifierExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.utilities.parseExpression

object ExistentialQuantifiers : QuantifierTranslationRule<AExistsQuantifierExpIR>
{
    override fun translate(irNode: AExistsQuantifierExpIR): CsExpression
    {
        val query = QuantifierUtilities.constructQuantifierQuerySyntax(irNode)
        return parseExpression("($query).Any(_ => _)")
    }
}
