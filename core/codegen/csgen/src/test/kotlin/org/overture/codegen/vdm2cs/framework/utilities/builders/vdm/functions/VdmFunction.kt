package org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.functions

import org.overture.codegen.vdm2cs.framework.utilities.SpekDescriptions
import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.VdmBuilder

class VdmFunction(val name: String,
                  val returnType: String,
                  val parameters: List<Pair<String, String>> = emptyList()) : VdmBuilder
{
    var body: String? = null;
    var precondition: String? = null;
    var postcondition: String? = null;

    fun body(body: String)
    {
        this.body = body
    }

    fun pre(precondition: String)
    {
        this.precondition = precondition
    }

    fun post(postcondition: String)
    {
        this.postcondition = postcondition
    }

    override val description: String
        get()
        {
            val formattedParameters = SpekDescriptions.formatParameters(parameters)
            val body = this.body

            return when (body)
            {
                null -> "an implicit function '$name' with $formattedParameters and a return type of '$returnType'"
                else -> "an explicit function '$name' with $formattedParameters that returns '$body'"
            }
        }

    override val syntax: String
        get()
        {
            val body = this.body
            val precondition = if (precondition != null) "pre $precondition\n" else ""
            val postcondition = when
            {
                postcondition != null -> "post $postcondition"
                body == null          -> "post true"
                else                  -> ""
            }

            return when (body)
            {
                null ->
                {
                    val formattedParameters = parameters.map { "${it.first}: ${it.second}" }.joinToString(", ")
                    val signature = "$name ($formattedParameters) r: $returnType\n";

                    "$signature$precondition$postcondition;\n"
                }
                else ->
                {
                    val parameterNames = parameters.map { it.first }.joinToString(", ")
                    val parameterTypes = parameters.map { it.second }.joinToString(" * ")
                    val signature = "$name: $parameterTypes -> $returnType\n$name($parameterNames) ==\n";

                    "$signature${body.prependIndent("    ")}\n$precondition$postcondition;\n"
                }
            }
        }
}
