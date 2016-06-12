package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.common.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsNamespaceDeclaration
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

class CsDocumentBuilder : CsBuilder<CsDocument>
{
    private val symbolDefinitions: MutableList<CsSymbolDefinition> = mutableListOf()
    private val usingDirectives: MutableList<CsUsingDirective> = mutableListOf()
    private var namespace: CsNamespaceDeclaration? = null

    override val ast: CsDocument
        get() = CsDocument(symbolDefinitions, usingDirectives, namespace!!)

    fun define(symbol: String)
    {
        symbolDefinitions += parseSymbolDefinition("#define $symbol")
    }

    fun using(importedType: String)
    {
        usingDirectives += parseUsingDirective("using $importedType;")
    }

    fun usingStatic(importedType: String)
    {
        usingDirectives += parseUsingStaticDirective("using static $importedType;")
    }

    fun namespace(name: String, initialiser: CsNamespaceDeclarationBuilder.() -> Unit = { })
    {
        namespace = CsNamespaceDeclarationBuilder(name).apply(initialiser).ast
    }
}
