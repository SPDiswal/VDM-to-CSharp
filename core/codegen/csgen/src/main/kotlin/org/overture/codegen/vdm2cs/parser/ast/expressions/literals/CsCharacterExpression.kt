package org.overture.codegen.vdm2cs.parser.ast.expressions.literals

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsCharacterExpression(val character: String) : CsExpression
