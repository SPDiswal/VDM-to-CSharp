package org.overture.codegen.vdm2cs.parser.ast.expressions.unary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

interface CsUnaryOperatorExpression : CsExpression
{
    val expression: CsExpression
}
