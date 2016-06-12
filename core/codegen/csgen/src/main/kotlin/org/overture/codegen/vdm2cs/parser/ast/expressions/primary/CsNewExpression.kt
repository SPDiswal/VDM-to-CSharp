package org.overture.codegen.vdm2cs.parser.ast.expressions.primary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier

data class CsNewExpression(val type: CsTypeIdentifier?,
                           val arguments: List<CsExpression>?,
                           val initialiser: CsInitialiserExpression?) : CsExpression
