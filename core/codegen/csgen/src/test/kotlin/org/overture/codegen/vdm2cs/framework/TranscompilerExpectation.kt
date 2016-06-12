package org.overture.codegen.vdm2cs.framework

data class TranscompilerExpectation(val description: String, val message: String, val action: (String) -> Unit)
