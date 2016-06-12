package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement

data class CsMethodDeclaration(val name: CsQualifiedNameIdentifier?,
                               val returnType: CsTypeIdentifier,
                               val typeParameters: List<CsNameIdentifier>?,
                               val parameters: List<Pair<CsNameIdentifier, CsTypeIdentifier>>,
                               val modifiers: List<CsModifier>,
                               val attributes: List<CsExpression>,
                               val statement: CsStatement) : CsDeclaration
