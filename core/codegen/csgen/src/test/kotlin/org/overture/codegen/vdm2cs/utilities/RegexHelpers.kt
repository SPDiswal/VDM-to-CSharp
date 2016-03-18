package org.overture.codegen.vdm2cs.utilities

/**
 * Constructs a regex pattern that matches anything, including newline characters.
 *
 * @return the regex pattern string '.*'.
 */
fun anything() = ".*"

/**
 * Constructs a regex pattern that matches the given string, potentially spanning multiple lines.
 *
 * @param contents the string to match.
 *
 * @return the escaped regex pattern string '\Qcontents\E'.
 */
fun a(contents: String) = contents.trimIndent().lines().map { Regex.escape(it.trim()) }.joinToString("\n")

/**
 * Constructs a regex pattern that matches the given header regex pattern string
 * followed by the given contents regex pattern string in curly braces.
 *
 * @param header the header regex pattern string to match.
 * @param contents the contents regex pattern string to match.
 *
 * @return the regex pattern string 'header\n\{contents\n\}\n'.
 */
fun a(header: String, contents: () -> String) = header + "\n\\{\n" + contents() + "\n\\}\n"

fun inTop(contents: () -> String) = contents() + anything()

fun inMidst(contents: () -> String) = anything() + contents() + anything()

fun inBottom(contents: () -> String) = anything() + contents()
