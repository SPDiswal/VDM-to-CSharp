package org.overture.codegen.vdm2cs.parser.ast.expressions.primary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsDictionaryInitialiserExpression(val items: List<Pair<List<CsExpression>, CsExpression>>) : CsInitialiserExpression
