package org.overture.codegen.vdm2cs.translations.expressions.binary.arithmetic

import org.overture.codegen.ir.expressions.APowerNumericBinaryExpIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Powers : BinaryExpressionTranslationRule<APowerNumericBinaryExpIR>
{
    override fun translate(irNode: APowerNumericBinaryExpIR): CsExpression
    {
        if (irNode.right.type is ARatNumericBasicTypeIR || irNode.right.type is ARealNumericBasicTypeIR)
        {
            throw UnsupportedTranslationException(
                "There is no translation for powers of fractional exponents available yet.")
        }

        return parseExpression("(${irNode.left.inline}).IntPower(${irNode.right.inline})")
    }
}
