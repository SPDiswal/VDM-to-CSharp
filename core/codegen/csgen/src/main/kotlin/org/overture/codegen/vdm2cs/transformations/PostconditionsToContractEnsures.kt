package org.overture.codegen.vdm2cs.transformations

import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.declarations.AMethodDeclIR
import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.vdm2cs.utilities.ir.contractEnsures

/**
 * This transformation invokes postconditions by Contract.Ensures in method declarations.
 */
final class PostconditionsToContractEnsures() : DepthFirstAnalysisAdaptor()
{
    override fun caseAMethodDeclIR(methodDeclaration: AMethodDeclIR)
    {
        val methodBody = methodDeclaration.body as ABlockStmIR
        val postconditionDeclaration = methodDeclaration.postCond as AMethodDeclIR?

        if (postconditionDeclaration != null)
        {
            val contractEnsuresInvocation = contractEnsures(postconditionDeclaration)
            methodBody.statements.addFirst(contractEnsuresInvocation);
        }
    }
}
