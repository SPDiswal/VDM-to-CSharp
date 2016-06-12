package org.overture.codegen.vdm2cs.parser.ast.statements

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsExpressionStatement(val expression: CsExpression) : CsStatement
