package org.overture.codegen.vdm2cs.translations.functionality.contracts

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.vdm2cs.common.*
import org.overture.codegen.vdm2cs.common.DefaultNames.STATE_NAME
import org.overture.codegen.vdm2cs.common.Remark.*
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.TranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Ensures : TranslationRule<AFuncDeclIR, CsStatement>
{
    override fun translate(irNode: AFuncDeclIR): CsStatement
    {
        val parent = irNode.parent()
        val postconditionName = irNode.name.toUpperCamelCase()
        val hasResultParameter = irNode.formalParams.any { it.pattern.hasRemark(RESULT) }
        val hasPreStateParameter = irNode.formalParams.any { it.pattern.hasRemark(PRE_STATE) }
        val hasPostStateParameter = irNode.formalParams.any { it.pattern.hasRemark(POST_STATE) }

        val postconditionArguments = when (parent)
        {
            is AFuncDeclIR   -> parent.formalParams.map { it.pattern.inline } +
                                result(parent.methodType.result.inline)

            is AMethodDeclIR -> (parent.formalParams.map { it.pattern.inline } +
                                 (if (hasResultParameter) result(parent.methodType.result.inline) else null) +
                                 (if (hasPreStateParameter) oldValue(STATE_NAME) else null) +
                                 (if (hasPostStateParameter) STATE_NAME else null))

            else             -> throw IllegalStateException()
        }.filterNotNull()

        return ensures("$postconditionName(${postconditionArguments.joinToString(", ")})")
    }
}
