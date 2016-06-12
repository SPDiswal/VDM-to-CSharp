package org.overture.codegen.vdm2cs.parser.ast.expressions.binary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

interface CsBinaryOperatorExpression : CsExpression
{
    val left: CsExpression
    val right: CsExpression
}
