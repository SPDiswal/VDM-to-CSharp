package org.overture.codegen.vdm2cs.framework.utilities

import org.overture.codegen.vdm2cs.parser.CsFormatter
import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.common.*
import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier.IMPLICIT
import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsIdentifier
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement

object CsDescriber
{
    fun describe(csNode: CsNode): String
    {
        val (article, description) = csNode.description
        return "$article${if (article != "") " " else ""}$description"
    }

    fun describe(csNode: CsNode, article: String) = "$article ${csNode.description.second}"

    private val CsNode.description: Pair<String, String>
        get() = when (this)
        {
            is CsDocument             -> "a" to "C# document"

            is CsSymbolDefinition     -> "a" to "symbol definition of '${symbol.name}'"

            is CsUsingDirective       -> "a" to "using directive for '${importedType.format}'"

            is CsIdentifier           -> "an" to "identifier '${this.format}'"

            is CsNamespaceDeclaration -> "a" to "namespace '${name.format}'"

            is CsClassDeclaration     ->
            {
                val formattedModifiers = modifiers.map { it.name.toLowerCase() }.joinToString(" ")
                "a" to "$formattedModifiers class '${name.format}'"
            }

            is CsMethodDeclaration    ->
            {
                val parametersDescription = SpekDescriptions.formatItems(parameters.map { it.second.format },
                                                                         singularDescription = "parameter",
                                                                         article = "a")

                val attributesDescription = SpekDescriptions.formatItems(attributes.map { it.format },
                                                                         singularDescription = "attribute",
                                                                         article = "an")

                val formattedModifiers = modifiers.map { it.name.toLowerCase() }.joinToString(" ")
                val formattedName = name?.format
                val formattedReturnType = returnType.format

                val constructorDescription = "a" to "$formattedModifiers constructor of '$formattedName' that has $parametersDescription"
                val unnamedMethodDescription = "a" to "$formattedModifiers method that returns '$formattedReturnType', has $parametersDescription and has $attributesDescription"
                val namedMethodDescription = "a" to "$formattedModifiers method '$formattedName' that returns '$formattedReturnType', has $parametersDescription and has $attributesDescription"

                when (formattedName)
                {
                    null -> when
                    {
                        !modifiers.contains(IMPLICIT) -> constructorDescription
                        else                          -> unnamedMethodDescription
                    }
                    else -> namedMethodDescription
                }
            }

            is CsPropertyDeclaration  -> "a" to "property '${name.format}' of type '${type.format}'"

            is CsExpression           -> "an" to "expression '${this.format.replace("\\s+".toRegex(), " ")}'"

            is CsStatement            -> "a" to "statement '${this.format.replace("\\s+".toRegex(), " ")}'"

            else                      -> throw IllegalStateException(this.javaClass.simpleName)
        }

    private val CsNode.format: String
        get() = CsFormatter.format(this)
}
