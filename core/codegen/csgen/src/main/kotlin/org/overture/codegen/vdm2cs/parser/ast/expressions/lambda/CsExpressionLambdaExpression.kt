package org.overture.codegen.vdm2cs.parser.ast.expressions.lambda

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*

data class CsExpressionLambdaExpression(val parameters: List<Pair<CsNameIdentifier, CsTypeIdentifier?>>,
                                        val expression: CsExpression) : CsExpression
