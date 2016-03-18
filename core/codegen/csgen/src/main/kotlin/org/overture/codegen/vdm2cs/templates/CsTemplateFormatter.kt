package org.overture.codegen.vdm2cs.templates

import org.overture.codegen.ir.PIR
import org.overture.codegen.ir.analysis.AnalysisException
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.merging.MergeVisitor
import java.io.StringWriter

/**
 * The C# template formatter provides convenience methods for strings in the Velocity templates.
 */
final class CsTemplateFormatter
{
    // TODO Determine when to parenthesise expressions to preserve semantics due to precedence rules.

    var mergeVisitor: MergeVisitor? = null

    /**
     * Processes an IR node according to its Velocity template.
     *
     * @param node the IR node to process.
     *
     * @return the output of the processed template.
     *
     * @throws AnalysisException if an error occurs when processing the IR node.
     */
    @Throws(AnalysisException::class)
    fun visit(node: PIR): String
    {
        val writer = StringWriter()
        node.apply<StringWriter>(mergeVisitor, writer)
        return writer.toString().trim()
    }

    /**
     * Processes a list of IR nodes according to their Velocity templates.
     *
     * @param nodes the list of IR nodes to process.
     *
     * @return a list of the corresponding outputs of the processed templates.
     *
     * @throws AnalysisException if an error occurs when processing an IR node.
     */
    @Throws(AnalysisException::class)
    fun visitAll(nodes: List<PIR>) = nodes.map {
        val writer = StringWriter()
        it.apply<StringWriter>(mergeVisitor, writer)
        writer.toString().trim()
    }

    /**
     * Processes a list of IR nodes according to their Velocity templates
     * and joins the list of results into a single string.
     *
     * @param nodes the list of IR nodes to process.
     * @param separator an optional delimiter to use between the strings in the list of results.
     *
     * @return the single combined string of the outputs of the processed templates.
     *
     * @throws AnalysisException if an error occurs when processing an IR node.
     */
    @Throws(AnalysisException::class)
    fun joinAll(nodes: List<PIR>, separator: String = "") = visitAll(nodes).joinToString(separator)

    fun formatAttributes(node: PIR): String
        = node.metaData
        .filter { it.value.startsWith("Attribute:") }
        .map { "[${it.value.removePrefix("Attribute:")}]" }
        .joinToString("\n")

    fun formatClassModifiers(classDeclaration: ADefaultClassDeclIR): String
    {
        val modifiers = mutableListOf("public")
        if (classDeclaration.static) modifiers.add("static")

        modifiers.addAll(classDeclaration.metaData
                             .filter { it.value.startsWith("Modifier:") }
                             .map { it.value.removePrefix("Modifier:") })

        return modifiers.joinToString(" ")
    }

    fun formatMethodModifiers(methodDeclaration: AMethodDeclIR): String
    {
        val modifiers = mutableListOf("public")
        if (methodDeclaration.static) modifiers.add("static")

        modifiers.addAll(methodDeclaration.metaData
                             .filter { it.value.startsWith("Modifier:") }
                             .map { it.value.removePrefix("Modifier:") })

        return modifiers.joinToString(" ")
    }
}
