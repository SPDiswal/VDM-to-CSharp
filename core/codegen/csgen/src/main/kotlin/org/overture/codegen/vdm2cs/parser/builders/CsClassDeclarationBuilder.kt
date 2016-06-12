package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.utilities.*

class CsClassDeclarationBuilder(name: String,
                                typeParameters: List<String>?,
                                superTypes: List<String>,
                                modifiers: List<String>,
                                attributes: List<String>) : CsBuilder<CsClassDeclaration>
{
    private val name = parseName(name)
    private val typeParameters = typeParameters?.map { parseName(it) }
    private val superTypes = superTypes.map { parseType(it) }
    private val modifiers = modifiers.map { parseModifier(it) }
    private val attributes = attributes.map { parseExpression(it) }
    private val members = mutableListOf<CsDeclaration>()

    override val ast: CsClassDeclaration
        get() = CsClassDeclaration(name, typeParameters, superTypes, modifiers, attributes, members)

    operator fun CsClassDeclarationBuilder.unaryPlus()
    {
        members += this.ast
    }

    operator fun CsMethodDeclarationBuilder.unaryPlus()
    {
        members += this.ast
    }

    operator fun CsPropertyDeclarationBuilder.unaryPlus()
    {
        members += this.ast
    }

    operator fun CsDeclaration.unaryPlus()
    {
        members += this
    }

    operator fun List<CsDeclaration>.unaryPlus()
    {
        members += this
    }
}
