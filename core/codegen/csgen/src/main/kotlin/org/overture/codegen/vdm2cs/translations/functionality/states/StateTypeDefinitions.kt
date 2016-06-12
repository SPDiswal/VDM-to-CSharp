package org.overture.codegen.vdm2cs.translations.functionality.states

import org.overture.codegen.ir.declarations.AStateDeclIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.parser.builders.publicSealedDataClass
import org.overture.codegen.vdm2cs.translations.functionality.functions.Functions
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.translations.types.definitions.TypeDefinitionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object StateTypeDefinitions : TypeDefinitionTranslationRule<AStateDeclIR>
{
    override fun translate(irNode: AStateDeclIR): CsClassDeclaration
    {
        val dataClassName = irNode.name.toUpperCamelCase()
        val fields = irNode.fields.map {
            val name = it.name.toUpperCamelCase()
            val type = it.type
            when
            {
                type.isStringType    -> StringFieldEntry(name, type.inline, isMutable = true)
                type is SSetTypeIR   -> SetFieldEntry(name, type.inline, isMutable = true)
                type is SSeqTypeIR   -> SequenceFieldEntry(name, type.inline, isMutable = true)
                type is SMapTypeIR   -> MappingFieldEntry(name, type.inline, isMutable = true)
                type is AUnionTypeIR -> UnionFieldEntry(name, type.inline, isMutable = true)
                type.isNullable      -> ObjectFieldEntry(name, type.inline, isMutable = true)
                else                 -> DefaultFieldEntry(name, type.inline, isMutable = true)
            }
        }

        val fieldInvariants = irNode.fields.map {
            TypeInvariants.generateInvariant(it.type, it.name.toUpperCamelCase())
        }

        val invariantDeclaration = irNode.invDecl
        val customInvariant = invariantDeclaration?.let { "${it.name.toUpperCamelCase()}(this)" }

        return publicSealedDataClass(dataClassName, fields, (fieldInvariants + customInvariant).filterNotNull())
        {
            if (invariantDeclaration != null)
                +invariantDeclaration.let { Functions.translate(it) }
        };
    }
}
