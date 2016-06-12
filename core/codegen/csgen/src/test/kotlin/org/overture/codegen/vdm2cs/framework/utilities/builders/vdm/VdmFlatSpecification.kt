package org.overture.codegen.vdm2cs.framework.utilities.builders.vdm

import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.functions.VdmFunction
import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.types.*

class VdmFlatSpecification : VdmCompositeBuilder()
{
    private val types: List<VdmType>
        get() = children.filter { it is VdmType }.map { it as VdmType }

    private val functions: List<VdmFunction>
        get() = children.filter { it is VdmFunction }.map { it as VdmFunction }

    fun recordType(name: String, initialiser: VdmRecordType.() -> Unit = { }) = add(VdmRecordType(name), initialiser)

    fun function(name: String, returnType: String, parameters: List<Pair<String, String>>,
                 initialiser: VdmFunction.() -> Unit = { })
        = add(VdmFunction(name, returnType, parameters), initialiser)

    override val description: String
        get() = combinedDescription("a flat specification")

    override val syntax: String
        get() = "${formatDefinitions("types", types)}" +
                "${formatDefinitions("functions", functions)}"

    private fun formatDefinitions(sectionName: String, definitions: List<VdmBuilder>)
        = "$sectionName\n" +
          definitions.map { it.syntax }.joinToString("\n").prependIndent("    ") +
          "\n"
}
