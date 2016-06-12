package org.overture.codegen.vdm2cs.parser.ast.identifiers

data class CsGenericTypeIdentifier(val typeName: CsQualifiedTypeNameIdentifier,
                                   val typeArguments: List<CsTypeIdentifier>) : CsTypeIdentifier
