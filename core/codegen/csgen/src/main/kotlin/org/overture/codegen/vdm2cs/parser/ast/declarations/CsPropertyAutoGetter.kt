package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier

data class CsPropertyAutoGetter(val modifiers: List<CsModifier>) : CsPropertyGetter
