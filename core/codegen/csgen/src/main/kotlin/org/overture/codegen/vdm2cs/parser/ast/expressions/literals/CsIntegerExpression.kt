package org.overture.codegen.vdm2cs.parser.ast.expressions.literals

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsIntegerExpression(val digits: String) : CsExpression
