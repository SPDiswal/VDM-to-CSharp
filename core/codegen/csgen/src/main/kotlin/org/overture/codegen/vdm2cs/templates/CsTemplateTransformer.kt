package org.overture.codegen.vdm2cs.templates

import org.overture.ast.util.ClonableString
import org.overture.codegen.ir.SExpIR
import org.overture.codegen.ir.declarations.ADefaultClassDeclIR
import org.overture.codegen.ir.expressions.ATypeArgExpIR
import org.overture.codegen.ir.name.ATypeNameIR
import org.overture.codegen.vdm2cs.utilities.ir.root

/**
 * The C# template transformer provides methods for transforming nodes in the Velocity templates.
 */
final class CsTemplateTransformer
{
    /**
     * Filters list of SExpIR nodes by removing all ATypeArgExpIR nodes.
     *
     * @param nodes the list of SExpIR nodes to filter.
     *
     * @return the filtered list of SExpIR nodes without any ATypeArgExpIR nodes.
     */
    fun removeTypeArgExp(nodes: List<SExpIR>) = nodes.filter { it !is ATypeArgExpIR }

    /**
     * Filters list of SExpIR nodes by keeping only ATypeArgExpIR nodes.
     *
     * @param nodes the list of SExpIR nodes to filter.
     *
     * @return the filtered list of ATypeArgExpIR nodes only.
     */
    fun retainTypeArgExp(nodes: List<SExpIR>) = nodes.filter { it is ATypeArgExpIR }

    fun removeRedundantQualifier(typeName: ATypeNameIR): ATypeNameIR
    {
        val enclosingClassDeclaration = typeName.root as ADefaultClassDeclIR
        val isQualifierRedundant = enclosingClassDeclaration.name == typeName.definingClass

        val result = ATypeNameIR()
        result.name = typeName.name
        result.definingClass = if (isQualifierRedundant) null else typeName.definingClass

        return result
    }

    fun extractStringValues(cloneableStrings: List<ClonableString>) = cloneableStrings.map { it.value }
}
