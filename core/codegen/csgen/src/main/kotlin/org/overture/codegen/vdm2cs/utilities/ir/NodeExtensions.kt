package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.ast.util.ClonableString
import org.overture.codegen.ir.*

fun PIR.addAttribute(attribute: String) = this.addMetadata("Attribute:$attribute")

fun PIR.addModifier(modifier: String) = this.addMetadata("Modifier:$modifier")

fun PIR.addMetadata(metadata: String)
{
    this.metaData += ClonableString(metadata)
}

val PIR.root: INode?
    get()
    {
        var parent = this.parent()
        var candidate = parent

        while (parent != null)
        {
            candidate = parent
            parent = parent.parent()
        }

        return candidate
    }
