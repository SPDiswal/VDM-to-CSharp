package org.overture.codegen.vdm2cs.parser.ast.common

import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsNamespaceDeclaration

data class CsDocument(val symbolDefinitions: List<CsSymbolDefinition>,
                      val usingDirectives: List<CsUsingDirective>,
                      val namespace: CsNamespaceDeclaration) : CsNode
