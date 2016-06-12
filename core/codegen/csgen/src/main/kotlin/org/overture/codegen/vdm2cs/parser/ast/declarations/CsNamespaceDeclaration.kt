package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsQualifiedNameIdentifier

data class CsNamespaceDeclaration(val name: CsQualifiedNameIdentifier,
                                  val members: List<CsDeclaration>) : CsDeclaration
