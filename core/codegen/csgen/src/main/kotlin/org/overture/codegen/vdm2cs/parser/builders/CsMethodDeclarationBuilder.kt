package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.common.CsModifier
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsMethodDeclaration
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsNameIdentifier
import org.overture.codegen.vdm2cs.parser.ast.statements.*
import org.overture.codegen.vdm2cs.utilities.*

class CsMethodDeclarationBuilder(name: String?,
                                 returnType: String,
                                 typeParameters: List<String>?,
                                 parameters: List<Pair<String, String>>,
                                 modifiers: List<String>,
                                 attributes: List<String>) : CsBuilder<CsMethodDeclaration>
{
    private val name = name?.let { parseQualifiedName(it) }
    private val returnType = parseType(returnType)
    private val typeParameters: List<CsNameIdentifier>? = typeParameters?.map { parseName(it) }
    private val parameters = parameters.map { parseName(it.first) to parseType(it.second) }
    private val modifiers = modifiers.map { parseModifier(it) }
    private val attributes = attributes.map { parseExpression(it) }
    private val statements: MutableList<CsStatement> = mutableListOf()

    override val ast: CsMethodDeclaration
        get() = CsMethodDeclaration(name, returnType, typeParameters, parameters, modifiers, attributes,
                                    when
                                    {
                                        statements.size == 1 && !isConstructorMethod -> statements.single()
                                        else                                         -> CsBlockStatement(statements)
                                    })

    private val isConstructorMethod: Boolean
        get() = name == null && !modifiers.contains(CsModifier.IMPLICIT)

    operator fun CsStatement.unaryPlus()
    {
        statements += this
    }

    operator fun List<CsStatement>.unaryPlus()
    {
        statements += this
    }

    val hasNoStatements: Boolean
        get() = statements.isEmpty()
}
