package org.overture.codegen.vdm2cs

import org.overture.ast.analysis.AnalysisException
import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.merging.*
import org.overture.codegen.utils.GeneratedData
import org.overture.codegen.vdm2cs.templates.*
import org.overture.codegen.vdm2cs.transformations.*
import java.io.StringWriter

/**
 * The VDM-to-C# transcompiler translates VDM-SL modules to C# classes.
 */
final class CsTranscompiler : CodeGenBase()
{
    private val formatter = CsSourceCodeFormatter()
    private val templateManager = CsTemplateManager()
    private val templateFormatter = CsTemplateFormatter()
    private val templateTransformer = CsTemplateTransformer()
    private val mergeVisitor = MergeVisitor(templateManager,
                                            arrayOf(TemplateCallable("template", templateFormatter),
                                                    TemplateCallable("transform", templateTransformer)))

    init
    {
        templateFormatter.mergeVisitor = this.mergeVisitor
    }

    private val transformations = listOf(
        FunctionsToPureMethods(transAssistant),
        TypeDeclarationsToDataClasses(),
        PostconditionsToContractEnsures(),
        PreconditionsToContractRequires(),
        SeqOfCharToString(transAssistant)
    )

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
        val modules = IRStatus.extract(IRStatus.extract(ir, AModuleDeclIR::class.java))
        modules.forEach { m -> generator.applyTotalTransformation(m, ModulesToStaticClassesInTopLevel(transAssistant)) }

        val classes = IRStatus.extract(modules, ADefaultClassDeclIR::class.java)
        classes.forEach { c -> transformations.forEach { t -> generator.applyPartialTransformation(c, t) } }

        val generatedData = GeneratedData()
        val generatedModules = classes.map { genIrModule(mergeVisitor, it) }

        generatedData.classes = generatedModules
        return generatedData
    }

    /**
     * Formats the C# source code according to the formatting procedure defined in the C# formatter.
     *
     * @param code the raw C# output from the processed Velocity templates.
     *
     * @return the formatted C# output.
     */
    override fun formatCode(code: StringWriter) = formatter.format(code.toString())
}
