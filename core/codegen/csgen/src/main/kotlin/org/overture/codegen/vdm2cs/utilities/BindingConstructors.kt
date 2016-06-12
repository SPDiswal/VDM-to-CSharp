package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.*
import org.overture.codegen.ir.patterns.*

fun setBinding(pattern: SPatternIR, set: SExpIR) = ASetBindIR().apply {
    this.pattern = pattern.clone()
    this.set = set.clone()
}

fun setBinding(patterns: List<SPatternIR>, set: SExpIR) = ASetMultipleBindIR().apply {
    this.patterns.addAll(patterns.map { it.clone() })
    this.set = set.clone()
}

fun typeBinding(pattern: SPatternIR, type: STypeIR) = ATypeBindIR().apply {
    this.pattern = pattern.clone()
    this.type = type.clone()
}

fun typeBinding(patterns: List<SPatternIR>, type: STypeIR) = ATypeMultipleBindIR().apply {
    this.patterns.addAll(patterns.map { it.clone() })
    this.type = type.clone()
}
