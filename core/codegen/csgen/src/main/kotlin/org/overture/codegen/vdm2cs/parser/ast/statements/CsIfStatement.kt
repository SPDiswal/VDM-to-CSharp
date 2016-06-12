package org.overture.codegen.vdm2cs.parser.ast.statements

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsIfStatement(val condition: CsExpression,
                         val thenStatement: CsStatement,
                         val elseStatement: CsStatement?) : CsStatement
