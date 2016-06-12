package org.overture.codegen.vdm2cs.parser.ast.declarations

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier

data class CsPropertyAutoSetter(val modifiers: List<CsModifier>) : CsPropertySetter
