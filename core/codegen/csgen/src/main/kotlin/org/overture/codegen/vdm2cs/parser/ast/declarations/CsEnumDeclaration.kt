package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsNameIdentifier

data class CsEnumDeclaration(val name: CsNameIdentifier,
                             val modifiers: List<CsModifier>,
                             val attributes: List<CsExpression>,
                             val members: List<CsEnumConstantDeclaration>) : CsDeclaration
