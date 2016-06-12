package org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.types

import org.overture.codegen.vdm2cs.framework.utilities.SpekDescriptions
import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.VdmCompositeBuilder
import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.fields.*

class VdmRecordType(val name: String) : VdmCompositeBuilder(), VdmType
{
    var invariant: String? = null;

    private val fields: List<VdmField>
        get() = children.filter { it is VdmField }.map { it as VdmField }

    fun inv(pattern: String, invariant: String)
    {
        this.invariant = "$pattern == $invariant"
    }

    fun field(name: String, type: String) = add(VdmCommonField(name, type))

    override val description: String
        get() = "a record type '$name' " +
                "with ${formatFields(fields)} "

    private fun formatFields(fields: List<VdmField>) = SpekDescriptions.formatItems(fields.map { it.type }, "field")

    override val syntax: String
        get()
        {
            val formattedFields = fields.map { it.syntax }.joinToString("\n").prependIndent("    ")
            val invariant = if (invariant != null) "\ninv $invariant" else ""
            return "$name :: \n$formattedFields$invariant;\n"
        }
}
