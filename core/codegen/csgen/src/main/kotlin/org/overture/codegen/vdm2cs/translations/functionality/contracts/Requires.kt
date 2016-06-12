package org.overture.codegen.vdm2cs.translations.functionality.contracts

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.vdm2cs.common.*
import org.overture.codegen.vdm2cs.common.DefaultNames.STATE_NAME
import org.overture.codegen.vdm2cs.common.Remark.*
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.TranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Requires : TranslationRule<AFuncDeclIR, CsStatement>
{
    override fun translate(irNode: AFuncDeclIR): CsStatement
    {
        val parent = irNode.parent()
        val preconditionName = irNode.name.toUpperCamelCase()
        val hasStateParameter = irNode.formalParams.any { it.pattern.hasRemark(STATE) }

        val preconditionArguments = when (parent)
        {
            is AFuncDeclIR   -> parent.formalParams.map { it.pattern.inline }

            is AMethodDeclIR -> (parent.formalParams.map { it.pattern.inline } +
                                 (if (hasStateParameter) STATE_NAME else null))

            else             -> throw IllegalStateException()
        }.filterNotNull()

        return requires("$preconditionName(${preconditionArguments.joinToString(", ")})")
    }
}
