package org.overture.codegen.vdm2cs.utilities

fun inNamespace(name: String, contents: () -> String) = a(a("namespace $name"), contents)

fun aNamespaceDeclaration(name: String) = a(a("namespace $name")) { anything() }

fun aUsingDirective(typeName: String) = a("using $typeName;")
