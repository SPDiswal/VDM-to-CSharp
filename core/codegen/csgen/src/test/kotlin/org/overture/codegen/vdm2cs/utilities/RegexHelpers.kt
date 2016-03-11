package org.overture.codegen.vdm2cs.utilities

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
