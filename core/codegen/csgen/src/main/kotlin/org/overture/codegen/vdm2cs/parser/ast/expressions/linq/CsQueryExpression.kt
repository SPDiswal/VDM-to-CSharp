package org.overture.codegen.vdm2cs.parser.ast.expressions.linq

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsNameIdentifier

data class CsQueryExpression(val fromSources: List<Pair<CsNameIdentifier, CsExpression>>,
                             val wherePredicate: CsExpression?,
                             val selectProjection: CsExpression) : CsExpression
