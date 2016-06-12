package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*

data class CsPropertyDeclaration(val name: CsQualifiedNameIdentifier,
                                 val type: CsTypeIdentifier,
                                 val getter: CsPropertyGetter?,
                                 val setter: CsPropertySetter?,
                                 val initialiser: CsExpression?,
                                 val modifiers: List<CsModifier>,
                                 val attributes: List<CsExpression>) : CsDeclaration
