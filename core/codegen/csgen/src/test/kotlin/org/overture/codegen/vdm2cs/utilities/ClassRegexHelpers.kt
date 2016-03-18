package org.overture.codegen.vdm2cs.utilities

fun inClass(name: String, contents: () -> String) = a("""${a("class $name")}(?: : [^\n]*)?""", contents)

fun inTopOfClass(name: String, contents: () -> String) = inClass(name) { inTop(contents) }

fun inMidstOfClass(name: String, contents: () -> String) = inClass(name) { inMidst(contents) }

fun inBottomOfClass(name: String, contents: () -> String) = inClass(name) { inBottom(contents) }

fun aClassDeclaration(name: String, implementedInterfaces: List<String> = emptyList()): String
{
    val formattedImplementedInterfaces = (if (implementedInterfaces.any()) " : " else "") +
                                         implementedInterfaces.map { it.replace("<>", "<$name>") }.joinToString(", ")

    return a(a("public sealed class $name$formattedImplementedInterfaces")) { anything() }
}

fun aStaticClassDeclaration(name: String) = a(a("public static class $name")) { anything() }
