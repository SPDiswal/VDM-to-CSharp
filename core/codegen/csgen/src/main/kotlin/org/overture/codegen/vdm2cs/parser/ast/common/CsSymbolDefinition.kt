package org.overture.codegen.vdm2cs.parser.ast.common

import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsNameIdentifier

data class CsSymbolDefinition(val symbol: CsNameIdentifier) : CsNode
