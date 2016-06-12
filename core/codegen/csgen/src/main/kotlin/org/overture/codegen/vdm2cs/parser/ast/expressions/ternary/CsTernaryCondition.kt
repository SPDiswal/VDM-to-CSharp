package org.overture.codegen.vdm2cs.parser.ast.expressions.ternary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsTernaryCondition(val condition: CsExpression,
                              val thenExpression: CsExpression,
                              val elseExpression: CsExpression) : CsExpression
