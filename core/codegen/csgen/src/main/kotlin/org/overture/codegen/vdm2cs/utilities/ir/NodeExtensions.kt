package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.ast.util.ClonableString
import org.overture.codegen.ir.PIR

fun PIR.addAttribute(attribute: String) = this.addMetadata("Attribute:$attribute")

fun PIR.addModifier(modifier: String) = this.addMetadata("Modifier:$modifier")

fun PIR.addMetadata(metadata: String)
{
    this.metaData += ClonableString(metadata)
}
