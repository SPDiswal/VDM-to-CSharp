package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.patterns.AIdentifierPatternIR

fun identifierPattern(name: String) = AIdentifierPatternIR().apply { this.name = name }
