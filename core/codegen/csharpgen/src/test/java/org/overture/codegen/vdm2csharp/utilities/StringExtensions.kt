package org.overture.codegen.vdm2csharp.utilities

import com.natpryce.hamkrest.assertion.assertThat

fun String.toTrimmedRegex()
    = (".*" + this.replace("\n", "\\s*") + ".*").toRegex(RegexOption.DOT_MATCHES_ALL)

fun String.mapLines(transform: (String) -> String)
    = this.lines().map(transform).joinToString("\n")

fun String.removeIndent()
    = this.mapLines { it.trimIndent() }

fun String.shouldContain(expectedOutput: String)
    = assertThat(this, com.natpryce.hamkrest.matches(expectedOutput.toTrimmedRegex()))
