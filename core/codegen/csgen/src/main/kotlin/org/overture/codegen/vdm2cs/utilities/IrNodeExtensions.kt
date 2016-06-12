package org.overture.codegen.vdm2cs.utilities

import org.overture.ast.util.ClonableString
import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.vdm2cs.common.Remark

fun PIR.addMetadata(metadata: String)
{
    this.metaData += ClonableString(metadata)
}

fun PIR.addRemark(remark: Remark) = this.addMetadata("Remark:${remark.name}")

fun PIR.hasRemark(remark: Remark) = metaData.any { it.value == "Remark:${remark.name}" }

val PIR.root: INode?
    get()
    {
        var parent = this.parent()
        var candidate = parent

        while (parent != null)
        {
            candidate = parent
            parent = parent.parent()
        }

        return candidate
    }

val PIR.enclosingSpecification: AModuleDeclIR?
    get() = this.root as? AModuleDeclIR

val PIR.enclosingFunction: AFuncDeclIR?
    get() = this.getAncestor(AFuncDeclIR::class.java)

val AModuleDeclIR.stateOfSpecification: AStateDeclIR?
    get() = this.decls.find { it is AStateDeclIR } as AStateDeclIR?

val AModuleDeclIR.valuesOfSpecification: List<AFieldDeclIR>
    get() = this.decls.filter { it is AFieldDeclIR && it.final }.map { it as AFieldDeclIR }

val AModuleDeclIR.functionsOfSpecification: List<AFuncDeclIR>
    get() = this.decls.filter { it is AFuncDeclIR }.map { it as AFuncDeclIR }

val AModuleDeclIR.operationsOfSpecification: List<AMethodDeclIR>
    get() = this.decls.filter { it is AMethodDeclIR }.map { it as AMethodDeclIR }

val AModuleDeclIR.unqualifiedNames: List<String>
    get() = (valuesOfSpecification.map { it.name } +
             functionsOfSpecification.map { it.name } +
             operationsOfSpecification.map { it.name })
