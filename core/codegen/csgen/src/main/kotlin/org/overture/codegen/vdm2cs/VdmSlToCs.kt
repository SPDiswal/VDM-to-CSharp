package org.overture.codegen.vdm2cs

import org.overture.ast.analysis.AnalysisException
import org.overture.ast.lex.Dialect
import org.overture.ast.modules.AModuleModules
import org.overture.codegen.vdm2cs.common.DefaultNames
import org.overture.codegen.vdm2cs.utilities.extractCsOutput
import org.overture.config.*
import org.overture.typechecker.util.TypeCheckerUtil
import java.io.File

fun main(args: Array<String>)
{
    ensureOutputDirectories()
    transcompileVdmSlFiles()
}

private val workingDirectoryName: String
    get() = System.getProperty("user.dir")

private val workingDirectory: File
    get() = File(workingDirectoryName)

private val outputDirectoryForModuleClasses: File
    get() = File("$workingDirectoryName/${DefaultNames.OUTPUT_FOLDER}")

private val outputDirectoryForQuoteEnums: File
    get() = File("$workingDirectoryName/${DefaultNames.OUTPUT_FOLDER}/${DefaultNames.QUOTES_OUTPUT_FOLDER}")

private val vdmSlFiles: List<File>
    get() = workingDirectory.listFiles({ directory, filename -> filename.endsWith(".vdmsl") }).toList()

private fun ensureOutputDirectories()
{
    if (!outputDirectoryForModuleClasses.exists())
        outputDirectoryForModuleClasses.mkdir()

    if (!outputDirectoryForQuoteEnums.exists())
        outputDirectoryForQuoteEnums.mkdir()
}

private fun transcompileVdmSlFiles()
{
    val abstractSyntaxTrees = parseFiles(vdmSlFiles)

    val transcompiler = VdmSlToCsTranscompilerAdapter()
    val transcompilationResult = transcompiler.generate(abstractSyntaxTrees)
    val generatedModuleClasses = transcompilationResult.classes.map { it to outputDirectoryForModuleClasses }
    val generatedQuoteEnums = transcompilationResult.quoteValues.map { it to outputDirectoryForQuoteEnums }

    for ((generatedDocument, destinationFolder) in (generatedModuleClasses + generatedQuoteEnums))
    {
        try
        {
            val csOutput = extractCsOutput(generatedDocument)
            val csFile = File(destinationFolder, "${generatedDocument.name}.cs");
            csFile.writeText(csOutput + "\n")
        }
        catch(e: AnalysisException)
        {
            println(e.message)
            println()
        }
    }
}

@Throws(AnalysisException::class)
private fun parseFiles(files: List<File>): List<AModuleModules>
{
    Settings.dialect = Dialect.VDM_SL
    Settings.release = Release.VDM_10

    val parseResult = TypeCheckerUtil.typeCheckSl(files)

    if (parseResult.parserResult.errors.any() || parseResult.result == null)
        throw AnalysisException(parseResult.parserResult.errorString)
    else if (parseResult.errors.any())
        throw AnalysisException(parseResult.errorString)
    else
        return parseResult.result
}
