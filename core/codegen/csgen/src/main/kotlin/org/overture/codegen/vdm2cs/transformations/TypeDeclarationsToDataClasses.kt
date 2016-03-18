package org.overture.codegen.vdm2cs.transformations

import org.overture.codegen.ir.SDeclIR
import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.types.ARecordTypeIR
import org.overture.codegen.trans.assistants.TransAssistantIR
import org.overture.codegen.vdm2cs.utilities.ir.*

/**
 * This transformation replaces a type declaration with a corresponding nested class declaration that implements
 * the ICopyable.Copy, IEquatable.Equals, object.Equals, object.GetHashCode and object.ToString methods.
 */
final class TypeDeclarationsToDataClasses() : DepthFirstAnalysisAdaptor()
{
    override fun caseANamedTypeDeclIR(namedTypeDeclaration: ANamedTypeDeclIR)
    {
        val name = namedTypeDeclaration.name.name
        val innerType = namedTypeDeclaration.type
        val fields = listOf(field("Value", innerType))

        transformTypeDeclarationToDataClass(namedTypeDeclaration, name, fields)
    }

    override fun caseARecordDeclIR(recordDeclaration: ARecordDeclIR)
    {
        val name = recordDeclaration.name
        val fields = recordDeclaration.fields

        transformTypeDeclarationToDataClass(recordDeclaration, name, fields)
    }

    private fun transformTypeDeclarationToDataClass(typeDeclaration: SDeclIR,
                                                    name: String,
                                                    fields: List<AFieldDeclIR>)
    {
        val type = classType(name)
        val dataClassDeclaration = dataClassDeclaration(type, name, fields)

        val enclosingTypeDeclaration = typeDeclaration.getAncestor(ATypeDeclIR::class.java)
        val enclosingClassDeclaration = typeDeclaration.getAncestor(ADefaultClassDeclIR::class.java)

        // TODO Preserve invariant of enclosing type declaration.

        enclosingClassDeclaration.innerClasses.add(dataClassDeclaration)
        enclosingClassDeclaration.typeDecls.remove(enclosingTypeDeclaration)
    }
}
