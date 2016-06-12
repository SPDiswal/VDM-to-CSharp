package org.overture.codegen.vdm2cs.translations.expressions.binary.mappings

import org.overture.codegen.ir.expressions.AMapUnionBinaryExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Merges : BinaryExpressionTranslationRule<AMapUnionBinaryExpIR>
{
    override fun translate(irNode: AMapUnionBinaryExpIR)
        = parseExpression("${irNode.left.inline}.Concat(${irNode.right.inline}).ToDictionary()")
}
