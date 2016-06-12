package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.expressions.*
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Applications : ExpressionTranslationRule<AApplyExpIR>
{
    // TODO Support type arguments (requires an enhancement of the IR).

    override fun translate(irNode: AApplyExpIR): CsExpression
    {
        val rootType = irNode.root.type
        val translatedRoot = irNode.root.inline
        val translatedArguments = irNode.args.joinExpressions

        return when (rootType)
        {
            is AMethodTypeIR -> parseExpression("$translatedRoot($translatedArguments)")
            is SMapTypeIR    -> parseExpression("$translatedRoot[$translatedArguments]")
            is SSeqTypeIR    ->
            {
                val index = irNode.args.first

                if (index is AIntLiteralExpIR)
                    parseExpression("$translatedRoot[${(index.value - 1)}]")
                else
                    parseExpression("$translatedRoot[$translatedArguments - 1]")
            }
            else             -> throw UnsupportedTranslationException(
                "There is no translation for applications of ${rootType.javaClass.simpleName} available yet.")
        }
    }
}
