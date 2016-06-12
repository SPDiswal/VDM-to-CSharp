package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*

data class CsClassDeclaration(val name: CsNameIdentifier,
                              val typeParameters: List<CsNameIdentifier>?,
                              val superTypes: List<CsTypeIdentifier>,
                              val modifiers: List<CsModifier>,
                              val attributes: List<CsExpression>,
                              val members: List<CsDeclaration>) : CsDeclaration
