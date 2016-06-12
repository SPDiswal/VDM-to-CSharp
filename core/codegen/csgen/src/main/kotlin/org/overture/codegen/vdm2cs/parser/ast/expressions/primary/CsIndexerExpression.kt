package org.overture.codegen.vdm2cs.parser.ast.expressions.primary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsIndexerExpression(val receiver: CsExpression, val arguments: List<CsExpression>) : CsExpression
