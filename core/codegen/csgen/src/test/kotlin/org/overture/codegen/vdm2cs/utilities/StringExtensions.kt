package org.overture.codegen.vdm2cs.utilities

import com.natpryce.hamkrest.assertion.assertThat
import org.overture.ast.analysis.AnalysisException
import org.overture.codegen.vdm2cs.CsTranscompiler
import org.overture.codegen.vdm2cs.utilities.*
import kotlin.text.RegexOption.DOT_MATCHES_ALL

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

fun String.shouldContain(expectedOutput: String)
{
    val trimmedOutput = anything() + expectedOutput.replace("\n\n", "\n").replace("\n", """\n(?:    )*""") + anything()
    assertThat(this, com.natpryce.hamkrest.matches(trimmedOutput.toRegex(DOT_MATCHES_ALL)))
}
