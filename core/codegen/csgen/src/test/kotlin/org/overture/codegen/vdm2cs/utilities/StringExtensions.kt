package org.overture.codegen.vdm2cs.utilities

import com.natpryce.hamkrest.assertion.assertThat
import org.overture.ast.analysis.AnalysisException
import org.overture.codegen.vdm2cs.CsTranscompiler

/**
 * Parses and type-checks the given string as a single VDM-SL document (either a flat specification or a single module)
 * and transcompiles it to a single top-level C# class, possibly with nested classes.
 *
 * @return the corresponding C# class or null if the VDM-SL specification is malformed.
 *
 * @throws AnalysisException if an error occurs during the process.
 */
@Throws(AnalysisException::class)
fun String.transcompile(): String
{
    val parsedInput = parse(this.trimIndent())
    val transcompiler = CsTranscompiler()
    val transcompiledData = transcompiler.generate(parsedInput)
    val csOutput = extractCsOutput(transcompiledData.classes.first())

    //    println(csOutput)

    return csOutput
}

fun String.toTrimmedRegex()
    = (".*" + this.replace("\n", "\\s*") + ".*").toRegex(RegexOption.DOT_MATCHES_ALL)

fun String.mapLines(transform: (String) -> String)
    = this.lines().map(transform).joinToString("\n")

fun String.removeIndent()
    = this.mapLines { it.trimIndent() }

fun String.shouldContain(expectedOutput: String)
    = assertThat(this, com.natpryce.hamkrest.matches(expectedOutput.toTrimmedRegex()))
