package org.overture.codegen.vdm2cs.framework

import org.overture.ast.analysis.AnalysisException
import org.overture.codegen.vdm2cs.VdmSlToCsTranscompilerAdapter
import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.*
import org.overture.codegen.vdm2cs.parser.CsParser
import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.utilities.*

abstract class TranscompilerIntegrationSpek : TranscompilerSpekBase()
{
    protected fun vdm(initialiser: VdmFlatSpecification.() -> Unit = { }): VdmFlatSpecification
    {
        val vdmFlatSpecification = VdmFlatSpecification()
        vdmFlatSpecification.initialiser()
        return vdmFlatSpecification
    }

    protected fun cs(className: String = "Global", cs: CsClassDeclarationBuilder.() -> Unit = { })
        = CsDocumentBuilder().apply {
        using("System")
        using("System.Diagnostics.Contracts")
        namespace("VdmToCs") { publicStaticClass(className, cs) }
    }.ast

    protected infix fun VdmBuilder.becomes(expectedCsAst: CsNode)
    {
        val vdm = this

        given(vdm.description)
        {
            on("transcompilation")
            {
                val actualCsOutput = vdm.syntax.transcompile()
                val actualCsAst = CsParser.parse(actualCsOutput)

                val expectations = makeExpectations(expectedCsAst, actualCsAst)

                for ((expectationDescription, message, action) in expectations)
                    it(expectationDescription)
                    {
                        action(message +
                               "\n\nwhen transcompiling\n\n" +
                               vdm.syntax.prependIndent("    ").trimEnd())
                    }
            }
        }
    }

    /**
     * Parses and type-checks the given string as a single VDM-SL document (either a flat specification or a single module)
     * and transcompiles it to a single top-level C# class, possibly with nested classes.
     *
     * @return the corresponding C# class or null if the VDM-SL specification is malformed.
     *
     * @throws AnalysisException if an error occurs during the process.
     */
    @Throws(AnalysisException::class)
    private fun String.transcompile(): String
    {
        val parsedInput = parse(this.trimIndent())
        val transcompiler = VdmSlToCsTranscompilerAdapter()
        val transcompiledData = transcompiler.generate(parsedInput)
        val csOutput = extractCsOutput(transcompiledData.classes.first())

        return csOutput
    }
}
