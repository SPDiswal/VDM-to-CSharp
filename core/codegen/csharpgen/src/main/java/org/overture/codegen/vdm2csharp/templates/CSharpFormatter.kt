package org.overture.codegen.vdm2csharp.templates

import org.overture.codegen.ir.INode
import org.overture.codegen.ir.analysis.AnalysisException
import org.overture.codegen.ir.declarations.AFormalParamLocalParamIR
import org.overture.codegen.merging.MergeVisitor
import java.io.StringWriter

/**
 * The C# formatter provides convenience methods for the Velocity templates,
 * utilising the standard library of Kotlin.
 */
final class CSharpFormatter()
{
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
    fun visit(node: INode): String
    {
        val writer = StringWriter()
        node.apply<StringWriter>(mergeVisitor, writer)
        return writer.toString()
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
    fun visitAll(nodes: List<INode>) = nodes.map {
        val writer = StringWriter()
        it.apply<StringWriter>(mergeVisitor, writer)
        writer.toString()
    }

    /**
     * Joins a list of strings into a single combined string.
     *
     * @param outputs the list of strings to join.
     * @param separator an optional delimiter to use between the strings in the list.
     *
     * @return the single combined string.
     */
    fun join(outputs: List<String>, separator: String = "") = outputs.joinToString(separator)

    /**
     * Transforms a list of AFormalParamLocalParamIR to a list of their respective SPatternIR.
     *
     * @param nodes the list of AFormalParamLocalParamIR to transform.
     *
     * @return a list of the corresponding SPatternIR.
     */
    fun extractNames(nodes: List<AFormalParamLocalParamIR>) = nodes.map { it.pattern }
}
