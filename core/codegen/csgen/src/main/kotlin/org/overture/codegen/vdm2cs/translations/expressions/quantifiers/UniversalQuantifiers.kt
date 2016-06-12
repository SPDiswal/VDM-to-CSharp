package org.overture.codegen.vdm2cs.translations.expressions.quantifiers

import org.overture.codegen.ir.expressions.AForAllQuantifierExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.utilities.parseExpression

object UniversalQuantifiers : QuantifierTranslationRule<AForAllQuantifierExpIR>
{
    override fun translate(irNode: AForAllQuantifierExpIR): CsExpression
    {
        val query = QuantifierUtilities.constructQuantifierQuerySyntax(irNode)
        return parseExpression("($query).All(_ => _)")
    }
}
