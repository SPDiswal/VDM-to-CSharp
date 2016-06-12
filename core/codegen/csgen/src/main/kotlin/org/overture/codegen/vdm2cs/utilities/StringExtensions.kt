package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.vdm2cs.parser.CsParser

fun String.toLowerCamelCase(): String
{
    val lowerCamelCased = this.toUpperCamelCase().decapitalize()

    return when
    {
        CsParser.reservedWords.contains(lowerCamelCased) -> "@$lowerCamelCased"
        else                                             -> lowerCamelCased
    }
}

fun String.toUpperCamelCase() = this.split("_").map {
    if (it.isEntirelyUpperCase) it.toLowerCase().capitalize()
    else it.capitalize()
}.joinToString("")

fun String.alignAt(index: Int) = let {
    val lines = this.lines()
    (listOf(lines.first()) + lines.drop(1).map { it.prependIndent(" ".repeat(index)) }).joinToString("\n")
}

val String.isEntirelyUpperCase: Boolean
    get() = this == this.toUpperCase()
