package org.overture.codegen.vdm2csharp

import org.overture.ast.analysis.AnalysisException
import org.overture.codegen.ir.*
import org.overture.codegen.ir.analysis.intf.IAnalysis
import org.overture.codegen.ir.declarations.AModuleDeclIR
import org.overture.codegen.merging.*
import org.overture.codegen.utils.GeneratedData
import org.overture.codegen.vdm2csharp.templates.*
import org.overture.codegen.vdm2csharp.transformations.SeqOfCharToString

/**
 * The VDM-to-C# transcompiler translates VDM-SL modules to C# classes.
 */
final class CSharpTranscompiler : CodeGenBase()
{
    private val templateManager = CSharpTemplateManager()
    private val formatter = CSharpFormatter()
    private val mergeVisitor = MergeVisitor(templateManager, arrayOf(TemplateCallable("formatter", formatter)))

    init
    {
        formatter.mergeVisitor = this.mergeVisitor
    }

    private val transformations = listOf<IAnalysis>(SeqOfCharToString(transAssistant))

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

        modules.forEach { m -> transformations.forEach { t -> generator.applyPartialTransformation(m, t) } }

        val generatedData = GeneratedData()
        val generatedModules = modules.map { genIrModule(mergeVisitor, it) }

        generatedData.classes = generatedModules
        return generatedData
    }
}
