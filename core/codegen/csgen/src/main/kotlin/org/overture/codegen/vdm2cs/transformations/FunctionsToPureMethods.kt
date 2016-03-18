package org.overture.codegen.vdm2cs.transformations

import org.overture.codegen.assistant.DeclAssistantIR
import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.trans.assistants.TransAssistantIR
import org.overture.codegen.vdm2cs.utilities.ir.*

/**
 * This transformation replaces occurrences of function declarations with corresponding method declarations
 * that have metadata for the .NET PureAttribute.
 */
final class FunctionsToPureMethods(val transform: TransAssistantIR) : DepthFirstAnalysisAdaptor()
{
    private val declaration: DeclAssistantIR
        get() = transform.info.declAssistant

    override fun caseAFuncDeclIR(functionDeclaration: AFuncDeclIR)
    {
        val methodDeclaration = declaration.funcToMethod(functionDeclaration)
        methodDeclaration.addAttribute("Pure")

        val blockStatement = ABlockStmIR()
        blockStatement.statements.add(methodDeclaration.body)

        methodDeclaration.body = blockStatement

        methodDeclaration.preCond?.apply(this)
        methodDeclaration.postCond?.apply(this)

        val enclosingClassDeclaration = functionDeclaration.getAncestor(ADefaultClassDeclIR::class.java)

        if (enclosingClassDeclaration != null)
        {
            enclosingClassDeclaration.functions.remove(functionDeclaration)
            enclosingClassDeclaration.methods.add(methodDeclaration)
        }
        else
        {
            transform.replaceNodeWith(functionDeclaration, methodDeclaration)
        }
    }
}
