package org.overture.codegen.vdm2cs.transformations

import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.declarations.AMethodDeclIR
import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.vdm2cs.utilities.ir.contractRequires

/**
 * This transformation invokes preconditions by Contract.Requires in method declarations.
 */
final class PreconditionsToContractRequires() : DepthFirstAnalysisAdaptor()
{
    override fun caseAMethodDeclIR(methodDeclaration: AMethodDeclIR)
    {
        val methodBody = methodDeclaration.body as ABlockStmIR
        val preconditionDeclaration = methodDeclaration.preCond as AMethodDeclIR?

        if (preconditionDeclaration != null)
        {
            val contractRequiresInvocation = contractRequires(preconditionDeclaration)
            methodBody.statements.addFirst(contractRequiresInvocation);
        }
    }
}
