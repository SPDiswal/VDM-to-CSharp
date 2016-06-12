package org.overture.codegen.vdm2cs.translations.functionality.contracts

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.patterns.AIdentifierPatternIR
import org.overture.codegen.vdm2cs.common.Remark
import org.overture.codegen.vdm2cs.utilities.*

object Postconditions
{
    fun applyRemarks(irNode: AFuncDeclIR)
    {
        val stateName = irNode.enclosingSpecification?.stateOfSpecification?.name

        val resultParameter = irNode.formalParams.find {
            val parameterName = it.pattern
            parameterName is AIdentifierPatternIR && parameterName.name == "RESULT"
        }
        resultParameter?.pattern?.addRemark(Remark.RESULT)

        if (irNode.parent() is AMethodDeclIR)
        {
            val preStateParameter = irNode.formalParams.find {
                val parameterName = it.pattern
                parameterName is AIdentifierPatternIR && parameterName.name == "_$stateName"
            }
            val postStateParameter = irNode.formalParams.find {
                val parameterName = it.pattern
                parameterName is AIdentifierPatternIR && parameterName.name == stateName
            }

            preStateParameter?.pattern?.addRemark(Remark.PRE_STATE)
            postStateParameter?.pattern?.addRemark(Remark.POST_STATE)
        }

        irNode.apply { this.addRemark(Remark.POSTCONDITION) }
    }
}
