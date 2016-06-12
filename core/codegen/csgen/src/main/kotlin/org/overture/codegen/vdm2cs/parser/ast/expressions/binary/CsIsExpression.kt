package org.overture.codegen.vdm2cs.parser.ast.expressions.binary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier

data class CsIsExpression(val expression: CsExpression, val type: CsTypeIdentifier) : CsExpression
