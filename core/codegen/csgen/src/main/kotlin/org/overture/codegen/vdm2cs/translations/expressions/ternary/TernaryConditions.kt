package org.overture.codegen.vdm2cs.translations.expressions.ternary

import org.overture.codegen.ir.expressions.ATernaryIfExpIR
import org.overture.codegen.vdm2cs.parser.ast.expressions.ternary.CsTernaryCondition
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.translate

object TernaryConditions : ExpressionTranslationRule<ATernaryIfExpIR>
{
    override fun translate(irNode: ATernaryIfExpIR)
        = CsTernaryCondition(irNode.condition.translate, irNode.trueValue.translate, irNode.falseValue.translate)
}
