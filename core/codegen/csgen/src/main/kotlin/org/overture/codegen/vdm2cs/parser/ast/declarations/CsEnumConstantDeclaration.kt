package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsNameIdentifier

data class CsEnumConstantDeclaration(val name: CsNameIdentifier, val value: CsExpression?) : CsDeclaration
