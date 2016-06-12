package org.overture.codegen.vdm2cs.parser.ast.expressions.lambda

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*
import org.overture.codegen.vdm2cs.parser.ast.statements.*

data class CsStatementLambdaExpression(val parameters: List<Pair<CsNameIdentifier, CsTypeIdentifier?>>,
                                       val statement: CsBlockStatement) : CsExpression
