package org.overture.codegen.vdm2cs.utilities

fun aPropertyDeclaration(type: String, name: String) = a("public $type $name { get; set; }")
