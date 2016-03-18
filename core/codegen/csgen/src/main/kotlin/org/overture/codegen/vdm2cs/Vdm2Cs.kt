package org.overture.codegen.vdm2cs

import org.overture.ast.analysis.AnalysisException
import org.overture.ast.lex.Dialect
import org.overture.ast.modules.AModuleModules
import org.overture.codegen.utils.*
import org.overture.codegen.vdm2cs.utilities.*
import org.overture.config.*
import org.overture.typechecker.util.TypeCheckerUtil
import java.io.File

fun main(args: Array<String>)
{
    ensureOutputDirectory()
    transcompileVdmSlFiles()
}

private val workingDirectoryName: String
    get() = System.getProperty("user.dir")

private val workingDirectory: File
    get() = File(workingDirectoryName)

private val outputDirectory: File
    get() = File("$workingDirectoryName/csgen-output")

private val vdmSlFiles: List<File>
    get() = workingDirectory.listFiles({ directory, filename -> filename.endsWith(".vdmsl") }).toList()

private fun ensureOutputDirectory()
{
    if (!outputDirectory.exists())
        outputDirectory.mkdir()
}

private fun transcompileVdmSlFiles()
{
    val generatedModules = vdmSlFiles.map { it.readText() }.map { transcompile(it).classes }.flatten()

    for (generatedModule in generatedModules)
    {
        try
        {
            val csOutput = extractCsOutput(generatedModule)
            val csFile = File(outputDirectory, "${generatedModule.name}.cs");
            csFile.writeText(csOutput)
        }
        catch(e: AnalysisException)
        {
            println(e.message)
            println()
        }
    }
}

private fun transcompile(input: String): GeneratedData
{
    val parsedInput = parse(input)
    val transcompiler = CsTranscompiler()
    return transcompiler.generate(parsedInput)
}
