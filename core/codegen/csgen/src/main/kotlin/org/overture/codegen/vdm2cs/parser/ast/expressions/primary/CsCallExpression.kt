package org.overture.codegen.vdm2cs.parser.ast.expressions.primary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier

data class CsCallExpression(val receiver: CsExpression,
                            val typeArguments: List<CsTypeIdentifier>?,
                            val arguments: List<CsExpression>) : CsExpression
