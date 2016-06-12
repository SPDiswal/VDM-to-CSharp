package org.overture.codegen.vdm2cs.parser.ast.expressions.unary

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsTypeIdentifier

data class CsTypeCastExpression(val type: CsTypeIdentifier, val expression: CsExpression) : CsExpression
