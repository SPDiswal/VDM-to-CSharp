package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.expressions.SVarExpIR
import org.overture.codegen.ir.name.ATokenNameIR
import org.overture.codegen.ir.patterns.AIdentifierPatternIR

fun field(name: String, type: STypeIR): AFieldDeclIR
{
    val field = AFieldDeclIR()
    field.name = name
    field.type = type

    return field;
}

fun sealedClassDeclaration(name: String,
                           implementedInterfaces: List<String> = emptyList()): ADefaultClassDeclIR
{
    val formattedImplementedInterfaces = implementedInterfaces.map {
        val tokenName = ATokenNameIR()
        tokenName.name = it.replace("<>", "<$name>")
        tokenName
    }

    val classDeclaration = ADefaultClassDeclIR()
    classDeclaration.name = name
    classDeclaration.static = false
    classDeclaration.addModifier("sealed")
    classDeclaration.superNames.addAll(formattedImplementedInterfaces)

    return classDeclaration
}

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
