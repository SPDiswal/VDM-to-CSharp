package org.overture.codegen.vdm2cs.parser.ast.common

import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsIdentifier

data class CsUsingDirective(val importedType: CsIdentifier, val isStatic: Boolean) : CsNode
