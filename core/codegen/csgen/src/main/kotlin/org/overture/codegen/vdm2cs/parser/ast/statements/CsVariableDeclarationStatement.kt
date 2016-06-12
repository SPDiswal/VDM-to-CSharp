package org.overture.codegen.vdm2cs.parser.ast.statements

import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.*

data class CsVariableDeclarationStatement(val identifier: CsNameIdentifier,
                                          val type: CsTypeIdentifier?,
                                          val initialiser: CsExpression?) : CsStatement
