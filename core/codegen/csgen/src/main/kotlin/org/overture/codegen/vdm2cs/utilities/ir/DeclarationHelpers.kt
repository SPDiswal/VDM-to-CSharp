package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.expressions.SVarExpIR
import org.overture.codegen.ir.patterns.AIdentifierPatternIR

fun SVarExpIR.asParameter() = parameter(this.name, this.type)

fun parameter(name: String, type: STypeIR): AFormalParamLocalParamIR
{
    val identifier = AIdentifierPatternIR()
    identifier.name = name

    val parameter = AFormalParamLocalParamIR()
    parameter.pattern = identifier
    parameter.type = type.clone()

    return parameter;
}

fun methodDeclaration(name: String,
                      parameters: List<AFormalParamLocalParamIR>,
                      returnType: STypeIR,
                      body: SStmIR): AMethodDeclIR
{
    val methodDeclaration = AMethodDeclIR()
    methodDeclaration.name = name
    methodDeclaration.formalParams.addAll(parameters.map { it.clone() })
    methodDeclaration.methodType = methodType(parameters.map { it.type }, returnType)
    methodDeclaration.implicit = false
    methodDeclaration.static = false
    methodDeclaration.body = body.clone()

    return methodDeclaration
}
