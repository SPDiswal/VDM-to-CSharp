package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.*
import org.overture.codegen.ir.expressions.ABoolLiteralExpIR
import org.overture.codegen.ir.statements.*
import org.overture.codegen.ir.types.ABoolBasicTypeIR

fun asStatement(expression: SExpIR): SStmIR
{
    val expressionStatement = AExpStmIR()
    expressionStatement.exp = expression.clone()

    return expressionStatement;
}

fun ifThen(ifCondition: SExpIR, thenStatement: SStmIR): SStmIR
{
    val ifThenStatement = AIfStmIR()
    ifThenStatement.ifExp = ifCondition.clone()
    ifThenStatement.thenStm = thenStatement.clone()

    return ifThenStatement;
}

fun `return`(expression: SExpIR): SStmIR
{
    val returnStatement = AReturnStmIR()
    returnStatement.exp = expression.clone()

    return returnStatement;
}

fun `return`(booleanValue: Boolean): SStmIR
{
    val booleanLiteral = ABoolLiteralExpIR()
    booleanLiteral.type = boolType()
    booleanLiteral.value = booleanValue

    val returnStatement = AReturnStmIR()
    returnStatement.exp = booleanLiteral

    return returnStatement;
}
