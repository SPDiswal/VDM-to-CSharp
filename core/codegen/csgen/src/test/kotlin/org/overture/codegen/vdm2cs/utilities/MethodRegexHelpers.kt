package org.overture.codegen.vdm2cs.utilities

fun inMethod(returnType: String,
             name: String,
             parameters: List<Pair<String, String>> = emptyList(),
             contents: () -> String): String
{
    val formattedParameters = parameters.map { "${it.first} ${it.second}" }.joinToString(", ")
    return a(a("$returnType $name($formattedParameters)"), contents)
}

fun inTopOfMethod(returnType: String,
                  name: String,
                  parameters: List<Pair<String, String>> = emptyList(),
                  contents: () -> String)
    = inMethod(returnType, name, parameters) { inTop(contents) }

fun inMidstOfMethod(returnType: String,
                    name: String,
                    parameters: List<Pair<String, String>> = emptyList(),
                    contents: () -> String)
    = inMethod(returnType, name, parameters) { inMidst(contents) }

fun inBottomOfMethod(returnType: String,
                     name: String,
                     parameters: List<Pair<String, String>> = emptyList(),
                     contents: () -> String)
    = inMethod(returnType, name, parameters) { inBottom(contents) }

fun aMethodDeclaration(returnType: String,
                       name: String,
                       parameters: List<Pair<String, String>> = emptyList(),
                       modifiers: List<String> = emptyList(),
                       attributes: List<String> = emptyList(),
                       contents: () -> String = { anything() }): String
{
    val formattedParameters = parameters.map { "${it.first} ${it.second}" }.joinToString(", ")
    val formattedModifiers = if (modifiers.any()) "${modifiers.joinToString(" ")} " else ""
    val formattedAttributes = if (attributes.any()) "${attributes.joinToString("\n")}\n" else ""

    return a(a("${formattedAttributes}public $formattedModifiers$returnType $name($formattedParameters)"), contents)
}

fun anOverridingMethodDeclaration(returnType: String,
                                  name: String,
                                  parameters: List<Pair<String, String>> = emptyList(),
                                  attributes: List<String> = emptyList(),
                                  contents: () -> String = { anything() })
    = aMethodDeclaration(returnType, name, parameters, listOf("override"), attributes, contents)

fun aPureStaticMethodDeclaration(returnType: String,
                                 name: String,
                                 parameters: List<Pair<String, String>> = emptyList(),
                                 contents: () -> String = { anything() })
    = aMethodDeclaration(returnType, name, parameters, listOf("static"), listOf("[Pure]"), contents)
