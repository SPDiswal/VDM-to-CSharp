package org.overture.codegen.vdm2cs.parser.ast.expressions.unary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsNotExpression(override val expression: CsExpression) : CsUnaryOperatorExpression
