package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.utilities.parseQualifiedName

class CsNamespaceDeclarationBuilder(name: String) : CsBuilder<CsNamespaceDeclaration>
{
    private val name = parseQualifiedName(name)
    private val members: MutableList<CsDeclaration> = mutableListOf()

    override val ast: CsNamespaceDeclaration
        get() = CsNamespaceDeclaration(name, members)

    operator fun CsDeclaration.unaryPlus()
    {
        members += this
    }

    operator fun List<CsDeclaration>.unaryPlus()
    {
        members += this
    }

    fun publicStaticClass(name: String, initialiser: CsClassDeclarationBuilder.() -> Unit = { })
        = +CsClassDeclarationBuilder(name,
                                     typeParameters = null,
                                     superTypes = emptyList(),
                                     modifiers = listOf("public", "static"),
                                     attributes = emptyList())
        .apply(initialiser).ast
}
