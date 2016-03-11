package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.AMethodDeclIR
import org.overture.codegen.ir.expressions.*
import org.overture.codegen.ir.patterns.AIdentifierPatternIR
import org.overture.codegen.ir.types.*

fun identifier(name: String, type: STypeIR): SVarExpIR
{
    val identifier = AIdentifierVarExpIR()
    identifier.isLambda = false
    identifier.isLocal = true
    identifier.name = name
    identifier.type = type.clone()

    return identifier;
}

fun qualifiedExternalIdentifier(qualifierName: String, name: String, type: STypeIR): SVarExpIR
{
    val qualifierType = AExternalTypeIR()
    qualifierType.name = qualifierName

    val qualifiedIdentifier = AExplicitVarExpIR()
    qualifiedIdentifier.isLambda = false
    qualifiedIdentifier.isLocal = false
    qualifiedIdentifier.classType = qualifierType
    qualifiedIdentifier.name = name
    qualifiedIdentifier.type = type.clone()

    return qualifiedIdentifier;
}

fun methodIdentifier(methodDeclaration: AMethodDeclIR) = identifier(methodDeclaration.name,
                                                                    methodDeclaration.methodType)

fun parameterIdentifiers(methodDeclaration: AMethodDeclIR)
    = methodDeclaration.formalParams
    .filter { it.pattern is AIdentifierPatternIR }
    .map {
        val parameterName = (it.pattern as AIdentifierPatternIR).name
        val parameterType = it.type
        identifier(parameterName, parameterType)
    }

fun typeArgument(type: STypeIR): SExpIR
{
    val typeParameter = ATypeArgExpIR()
    typeParameter.type = type.clone()

    return typeParameter;
}

fun invoke(methodName: String, arguments: List<SExpIR>, returnType: STypeIR): SExpIR
{
    val methodType = methodType(arguments.map { it.type }, returnType)
    val methodIdentifier = identifier(methodName, methodType)

    return invoke(methodIdentifier, arguments, returnType)
}

fun invoke(qualifierName: String, methodName: String, arguments: List<SExpIR>, returnType: STypeIR): SExpIR
{
    val methodType = methodType(arguments.map { it.type }, returnType)
    val qualifiedMethodIdentifier = qualifiedExternalIdentifier(qualifierName, methodName, methodType)

    return invoke(qualifiedMethodIdentifier, arguments, returnType)
}

fun invoke(methodIdentifier: SVarExpIR, arguments: List<SExpIR>, returnType: STypeIR): SExpIR
{
    val methodType = AMethodTypeIR()
    methodType.params.addAll(arguments.map { it.type.clone() })
    methodType.result = returnType.clone()

    val methodInvocation = AApplyExpIR()
    methodInvocation.type = returnType.clone()
    methodInvocation.root = methodIdentifier.clone()
    methodInvocation.args.addAll(arguments.map { it.clone() })

    return methodInvocation
}

fun invokeGetter(objectIdentifier: SVarExpIR, propertyIdentifier: SVarExpIR): SExpIR
{
    val getterInvocation = AFieldExpIR()
    getterInvocation.type = propertyIdentifier.type.clone()
    getterInvocation.`object` = objectIdentifier.clone()
    getterInvocation.memberName = propertyIdentifier.name

    return getterInvocation
}

fun `null`(type: STypeIR = AUnknownTypeIR()): SExpIR
{
    val nullExpression = ANullExpIR()
    nullExpression.type = type

    return nullExpression
}

fun `this`(type: STypeIR = AUnknownTypeIR()): SExpIR
{
    val thisExpression = ASelfExpIR()
    thisExpression.type = type

    return thisExpression
}

fun booleanLiteral(booleanValue: Boolean): SExpIR
{
    val booleanLiteral = ABoolLiteralExpIR()
    booleanLiteral.type = boolType()
    booleanLiteral.value = booleanValue

    return booleanLiteral
}

fun isOfType(expression: SExpIR, type: STypeIR): SExpIR
{
    val isOfTypeExpression = AInstanceofExpIR()
    isOfTypeExpression.type = boolType()
    isOfTypeExpression.exp = expression.clone()
    isOfTypeExpression.checkedType = type.clone()

    return isOfTypeExpression
}

fun typeCast(expression: SExpIR, type: STypeIR): SExpIR
{
    val typeCastExpression = ACastUnaryExpIR()
    typeCastExpression.type = type.clone()
    typeCastExpression.exp = expression.clone()

    return typeCastExpression
}

fun binaryAnd(left: SExpIR, right: SExpIR): SExpIR
{
    val andExpression = AAndBoolBinaryExpIR()
    andExpression.type = boolType()
    andExpression.left = left.clone()
    andExpression.right = right.clone()

    return andExpression
}

fun binaryEquals(left: SExpIR, right: SExpIR): SExpIR
{
    // TODO Handle specific cases of Equals.
    // e.g. Double.Compare for floating point numbers and SequenceEquals/SetEquals for sequences/sets

    val equalsExpression = AEqualsBinaryExpIR()
    equalsExpression.type = boolType()
    equalsExpression.left = left.clone()
    equalsExpression.right = right.clone()

    return equalsExpression
}
