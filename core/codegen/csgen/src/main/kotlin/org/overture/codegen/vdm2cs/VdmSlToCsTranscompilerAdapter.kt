package org.overture.codegen.vdm2cs

import org.overture.ast.analysis.AnalysisException
import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.AModuleDeclIR
import org.overture.codegen.utils.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.utilities.format

/**
 * The VDM-SL-to-C# transcompiler translates VDM-SL modules to C# classes.
 */
class VdmSlToCsTranscompilerAdapter : CodeGenBase()
{
    /**
     * Transcompiles a collection of IR nodes representing VDM-SL modules to a collection of static C# classes.
     *
     * @param ir the status-enhanced IR nodes to transcompile.
     *
     * @return the generated C# classes as well as information about the code generation process.
     *
     * @throws AnalysisException if an error occurs during the transcompilation process.
     */
    @Throws(AnalysisException::class)
    override fun genVdmToTargetLang(ir: List<IRStatus<PIR>>): GeneratedData
    {
        val modules = IRStatus.extract(ir, AModuleDeclIR::class.java)

        val translatedModules = mutableListOf<GeneratedModule>()
        val fileNamesInUse = mutableListOf<String>()

        for (module in modules)
        {
            val irNode = module.irNode
            val moduleClassDocument = VdmSlToCsTranscompiler.transcompileSpecification(irNode)
            val moduleClassName = (moduleClassDocument.namespace.members.single() as CsClassDeclaration).name.format

            val outputFileName = generateOutputFileName(moduleClassName, fileNamesInUse)
            fileNamesInUse += outputFileName

            translatedModules += GeneratedModule(outputFileName, irNode, moduleClassDocument.format, false)
        }

        return GeneratedData().apply {
            classes = translatedModules
            quoteValues = listOf(generateQuoteEnum())
        }
    }

    private fun generateOutputFileName(moduleClassName: String, fileNamesInUse: List<String>): String
    {
        var outputFileName = moduleClassName
        var counter = 0

        while (fileNamesInUse.contains(outputFileName))
        {
            counter++
            outputFileName = moduleClassName + counter
        }

        return outputFileName
    }

    private fun generateQuoteEnum(): GeneratedModule
    {
        val quoteEnumDocument = VdmSlToCsTranscompiler.transcompileQuotes(generator.quoteValues)
        return GeneratedModule("Quote", null, quoteEnumDocument.format, false)
    }
}
