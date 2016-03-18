package org.overture.codegen.vdm2cs.transformations

import org.overture.ast.util.ClonableString
import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.trans.*
import org.overture.codegen.trans.assistants.TransAssistantIR
import org.overture.codegen.vdm2cs.CS_NAMESPACE

final class ModulesToStaticClassesInTopLevel(val transform: TransAssistantIR) : DepthFirstAnalysisAdaptor(), ITotalTransformation
{
    private val innerTransformation = ModuleToClassTransformation(transform.info, transform, emptyList())
    private var result: ADefaultClassDeclIR? = null

    override fun caseAModuleDeclIR(moduleDeclaration: AModuleDeclIR)
    {
        innerTransformation.caseAModuleDeclIR(moduleDeclaration)
        val classDeclaration = innerTransformation.result as ADefaultClassDeclIR

        val privateConstructor = classDeclaration.methods.single { it.isConstructor }
        classDeclaration.methods.remove(privateConstructor)
        classDeclaration.static = true;
        classDeclaration.`package` = CS_NAMESPACE;

        // TODO Optimise list of dependencies to include only the ones needed, not necessarily all of them.

        classDeclaration.dependencies += listOf(ClonableString("System"),
                                                ClonableString("System.Diagnostics.Contracts"))

        result = classDeclaration
    }

    override fun getResult() = result
}
