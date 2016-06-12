package org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.fields

class VdmCommonField(override val name: String, override val type: String) : VdmField
{
    override val description: String
        get() = "a field '$name' of type '$type'"

    override val syntax: String
        get() = "$name : $type"
}
