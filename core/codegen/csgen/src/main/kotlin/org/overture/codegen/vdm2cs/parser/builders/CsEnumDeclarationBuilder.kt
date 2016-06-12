package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.utilities.*

class CsEnumDeclarationBuilder(name: String, modifiers: List<String>, attributes: List<String>) : CsBuilder<CsEnumDeclaration>
{
    private val name = parseName(name)
    private val modifiers = modifiers.map { parseModifier(it) }
    private val attributes = attributes.map { parseExpression(it) }
    private val members = mutableListOf<CsEnumConstantDeclaration>()

    override val ast: CsEnumDeclaration
        get() = CsEnumDeclaration(name, modifiers, attributes, members)

    operator fun CsEnumConstantDeclaration.unaryPlus()
    {
        members += this
    }

    operator fun List<CsEnumConstantDeclaration>.unaryPlus()
    {
        members += this
    }
}
