package org.overture.codegen.vdm2csharp.utilities

import org.overture.ast.analysis.AnalysisException
import org.overture.ast.lex.Dialect
import org.overture.ast.modules.AModuleModules
import org.overture.codegen.vdm2csharp.CSharpTranscompiler
import org.overture.config.*
import org.overture.typechecker.util.TypeCheckerUtil

abstract class TranscompilerSpecification
{
    /**
     * Parses, type-checks and transcompiles a single VDM-SL document to a single C# class.
     *
     * @param input the VDM-SL specification which is either a flat specification or a single module.
     *
     * @return the corresponding C# class or null if the VDM-SL specification is malformed.
     *
     * @throws AnalysisException if an error occurs during the process.
     */
    @Throws(AnalysisException::class)
    fun transcompileSingleDocument(input: String): String
    {
        val transcompiler = CSharpTranscompiler()
        val parsedInput = parse(input.trimIndent())
        val transcompiledData = transcompiler.generate(parsedInput)
        val output = transcompiledData.classes.map {
            when
            {
                !it.hasUnsupportedTargLangNodes() -> it.content
                else                              -> throw AnalysisException(it.unsupportedInTargLang
                                                                                 .map { "[${it.node.javaClass}] ${it.reason}" }
                                                                                 .joinToString("\n"))
            }
        }.first()

        return output
    }

    private fun parse(input: String): List<AModuleModules>
    {
        Settings.dialect = Dialect.VDM_SL
        Settings.release = Release.VDM_10
        return TypeCheckerUtil.typeCheckSl(input).result
    }

    //region HELPERS: Regex

    /**
     * Constructs a regex pattern that matches anything.
     *
     * @return the regex pattern string '.*'.
     */
    fun anything() = ".*"

    /**
     * Constructs a regex pattern that matches whitespace.
     *
     * @return the regex pattern string '\s*'.
     */
    fun whitespace() = "\\s*"

    /**
     * Constructs a regex pattern that matches the given string, potentially spanning multiple lines.
     *
     * @param body the string to match.
     *
     * @return the escaped regex pattern string '\Qbody\E'.
     */
    fun a(body: String) = body.mapLines { Regex.escape(it.removeIndent()) }

    /**
     * Constructs a regex pattern that matches the given header string
     * followed by the given body string in curly braces.
     *
     * @param header the header string to match.
     * @param body the body string to match.
     *
     * @return the escaped regex pattern string '\Qheader\E\{body\}'.
     */
    fun a(header: String, body: () -> String)
        = """
        ${a(header)}
        \{
        ${body()}
        \}
        """.removeIndent()

    /**
     * Constructs a regex pattern that matches the given header string
     * preceded by the given attribute string and followed by the given body string in curly braces.
     *
     * @param attribute the attribute string to match.
     * @param header the header string to match.
     * @param body the body string to match.
     *
     * @return the escaped regex pattern string '\Qattribute\E\s*\Qheader\E\{body\}'.
     */
    fun a(attribute: String, header: String, body: () -> String)
        = """
        ${a(attribute)}
        ${a(header, body)}
        """.removeIndent()

    //endregion
}
