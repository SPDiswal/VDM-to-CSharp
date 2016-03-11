package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.SStmIR
import org.overture.codegen.ir.declarations.AMethodDeclIR
import org.overture.codegen.ir.types.*

fun contractRequires(preconditionDeclaration: AMethodDeclIR): SStmIR
{
    val preconditionIdentifier = methodIdentifier(preconditionDeclaration)
    val parameterIdentifiers = parameterIdentifiers(preconditionDeclaration)

    // pre_Method(a, b)
    val preconditionInvocation = invoke(preconditionIdentifier,
                                        parameterIdentifiers,
                                        boolType())

    // Contract.Requires(pre_Method(...))
    val contractRequiresInvocation = invoke("Contract",
                                            "Requires",
                                            listOf(preconditionInvocation),
                                            voidType())

    return asStatement(contractRequiresInvocation)
}

fun contractEnsures(postconditionDeclaration: AMethodDeclIR): SStmIR
{
    val postconditionIdentifier = methodIdentifier(postconditionDeclaration)
    val parameterIdentifiers = parameterIdentifiers(postconditionDeclaration).dropLast(1)

    val resultType = postconditionDeclaration.methodType.params.last()
    val resultTypeArgument = typeArgument(resultType)

    // Contract.Result<TResultType>()
    val contractResultInvocation = invoke("Contract", "Result", listOf(resultTypeArgument), resultType)

    // post_Method(a, b, Contract.Result<TResultType>())
    val postconditionInvocation = invoke(postconditionIdentifier,
                                         parameterIdentifiers + contractResultInvocation,
                                         boolType())

    // Contract.Ensures(post_Method(...))
    val contractEnsuresInvocation = invoke("Contract",
                                           "Ensures",
                                           listOf(postconditionInvocation),
                                           voidType())

    return asStatement(contractEnsuresInvocation)
}
