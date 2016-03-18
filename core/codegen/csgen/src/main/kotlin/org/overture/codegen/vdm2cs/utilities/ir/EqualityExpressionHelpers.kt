package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.expressions.SVarExpIR

fun referenceEqualsNull(type: STypeIR, otherIdentifier: SVarExpIR)
    = invoke("ReferenceEquals",
             listOf(`null`(type), otherIdentifier),
             boolType())

fun referenceEqualsThis(type: STypeIR, otherIdentifier: SVarExpIR)
    = invoke("ReferenceEquals",
             listOf(`this`(type), otherIdentifier),
             boolType())
