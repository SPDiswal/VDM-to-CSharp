package org.overture.codegen.vdm2cs.parser.ast.expressions.primary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression

data class CsCollectionInitialiserExpression(val items: List<CsExpression>) : CsInitialiserExpression
