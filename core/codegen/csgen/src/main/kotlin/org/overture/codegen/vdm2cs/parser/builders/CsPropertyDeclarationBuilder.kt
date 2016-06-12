package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.utilities.*

class CsPropertyDeclarationBuilder(name: String, type: String,
                                   modifiers: List<String>) : CsBuilder<CsPropertyDeclaration>
{
    private val name = parseQualifiedName(name)
    private val type = parseType(type)
    private var getter: CsPropertyGetter? = null
    private var setter: CsPropertySetter? = null
    private var initialiser: CsExpression? = null
    private val modifiers = modifiers.map { parseModifier(it) }
    private val attributes = emptyList<CsExpression>()

    override val ast: CsPropertyDeclaration
        get() = CsPropertyDeclaration(name, type, getter, setter, initialiser, modifiers, attributes)

    fun privateAutoGetter()
    {
        getter = CsPropertyAutoGetter(listOf(CsModifier.PRIVATE))
    }

    fun publicAutoGetter()
    {
        getter = CsPropertyAutoGetter(emptyList())
    }

    fun privateAutoSetter()
    {
        setter = CsPropertyAutoSetter(listOf(CsModifier.PRIVATE))
    }

    fun publicAutoSetter()
    {
        setter = CsPropertyAutoSetter(emptyList())
    }

    fun initialiser(expression: String)
    {
        initialiser = parseExpression(expression)
    }
}
