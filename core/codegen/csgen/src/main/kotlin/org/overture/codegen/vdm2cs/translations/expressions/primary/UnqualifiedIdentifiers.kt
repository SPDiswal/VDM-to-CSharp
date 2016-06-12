package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.expressions.AIdentifierVarExpIR
import org.overture.codegen.ir.types.AMethodTypeIR
import org.overture.codegen.vdm2cs.common.DefaultNames.PRE_STATE_PREFIX
import org.overture.codegen.vdm2cs.common.DefaultNames.RESULT_NAME
import org.overture.codegen.vdm2cs.common.DefaultNames.STATE_NAME
import org.overture.codegen.vdm2cs.common.Remark
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object UnqualifiedIdentifiers : ExpressionTranslationRule<AIdentifierVarExpIR>
{
    override fun translate(irNode: AIdentifierVarExpIR): CsExpression
    {
        val lowerCamelCased = irNode.name.toLowerCamelCase()
        val upperCamelCased = irNode.name.toUpperCamelCase()

        val unqualifiedNames = irNode.enclosingSpecification?.unqualifiedNames ?: emptyList()

        val stateName = irNode.enclosingSpecification?.stateOfSpecification?.name
        val stateParameter = stateName?.toLowerCamelCase()
        val preStateParameter = stateParameter?.let { "$PRE_STATE_PREFIX${it.capitalize()}" }

        val isInPrecondition = irNode.enclosingFunction?.hasRemark(Remark.PRECONDITION) ?: false
        val isInPostcondition = irNode.enclosingFunction?.hasRemark(Remark.POSTCONDITION) ?: false

        val translatedName = when
        {
            isInPrecondition || isInPostcondition  -> when
            {
                irNode.name == "RESULT"                -> RESULT_NAME
                irNode.name == stateName               -> stateParameter!!
                irNode.name == "_$stateName"           -> preStateParameter!!
                irNode.name.startsWith("pre_") ||
                irNode.name.startsWith("post_")        -> upperCamelCased
                irNode.name.startsWith("_")            -> "$preStateParameter.$upperCamelCased"
                unqualifiedNames.contains(irNode.name) -> upperCamelCased
                !irNode.isLocal                        -> "$stateParameter.$upperCamelCased"
                else                                   -> lowerCamelCased
            }

            unqualifiedNames.contains(irNode.name) -> upperCamelCased

            !irNode.isLocal                        -> "$STATE_NAME.$upperCamelCased"

            irNode.type is AMethodTypeIR           -> upperCamelCased

            else                                   -> lowerCamelCased
        }

        return parseExpression(translatedName)
    }
}
