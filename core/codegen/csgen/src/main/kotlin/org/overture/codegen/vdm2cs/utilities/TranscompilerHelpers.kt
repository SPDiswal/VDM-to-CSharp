package org.overture.codegen.vdm2cs.utilities

import org.overture.ast.analysis.AnalysisException
import org.overture.ast.lex.Dialect
import org.overture.ast.modules.AModuleModules
import org.overture.codegen.utils.GeneratedModule
import org.overture.config.*
import org.overture.typechecker.util.TypeCheckerUtil

@Throws(AnalysisException::class)
fun parse(input: String): List<AModuleModules>
{
    Settings.dialect = Dialect.VDM_SL
    Settings.release = Release.VDM_10

    val parseResult = TypeCheckerUtil.typeCheckSl(input)

    if (parseResult.parserResult.errors.any() || parseResult.result == null)
        throw AnalysisException(parseResult.parserResult.errorString)
    else if (parseResult.errors.any())
        throw AnalysisException(parseResult.errorString)
    else
        return parseResult.result
}

@Throws(AnalysisException::class)
fun extractCsOutput(generatedModule: GeneratedModule): String
{
    if (!generatedModule.hasUnsupportedTargLangNodes() && !generatedModule.hasMergeErrors())
        return generatedModule.content
    else
    {
        val errorDescription = generatedModule.unsupportedInTargLang.map {
            val nodePackageName = it.node.javaClass.`package`.toString().split(".").last()
            val nodeTemplateName = it.node.javaClass.simpleName
            "[$nodePackageName.$nodeTemplateName] ${it.reason}"
        }.plus(generatedModule.mergeErrors.map { it.message }).joinToString("\n")

        throw AnalysisException(errorDescription)
    }
}
