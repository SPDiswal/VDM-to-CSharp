package org.overture.codegen.vdm2cs.translations.functionality.contracts

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.patterns.AIdentifierPatternIR
import org.overture.codegen.vdm2cs.common.Remark
import org.overture.codegen.vdm2cs.utilities.*

object Preconditions
{
    fun applyRemarks(irNode: AFuncDeclIR)
    {
        val stateName = irNode.enclosingSpecification?.stateOfSpecification?.name

        if (irNode.parent() is AMethodDeclIR)
        {
            val stateParameter = irNode.formalParams.find {
                val parameterName = it.pattern
                parameterName is AIdentifierPatternIR && parameterName.name == stateName
            }
            stateParameter?.pattern?.addRemark(Remark.STATE)
        }

        irNode.apply { this.addRemark(Remark.PRECONDITION) }
    }
}
