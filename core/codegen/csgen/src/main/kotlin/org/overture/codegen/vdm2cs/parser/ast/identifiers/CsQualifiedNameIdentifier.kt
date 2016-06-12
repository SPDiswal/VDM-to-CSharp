package org.overture.codegen.vdm2cs.parser.ast.identifiers

data class CsQualifiedNameIdentifier(val qualifiers: List<CsNameIdentifier>,
                                     val identifier: CsNameIdentifier) : CsNameIdentifier
{
    override val name = identifier.name
}
