package org.overture.codegen.vdm2cs.translations.types.definitions

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.parser.builders.publicSealedDataClass
import org.overture.codegen.vdm2cs.translations.functionality.functions.Functions
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

object CompositeTypeDefinitions : TypeDefinitionTranslationRule<ATypeDeclIR>
{
    // TODO Filter out equality abstraction fields from comparisons (requires an enhancement of the IR).

    override fun translate(irNode: ATypeDeclIR): CsClassDeclaration
    {
        val inner = irNode.decl as ARecordDeclIR
        val recordName = inner.name.toUpperCamelCase()

        val fields = inner.fields.map {
            val name = it.name.toUpperCamelCase()
            val type = it.type
            when
            {
                type.isStringType    -> DataFieldEntry.StringFieldEntry(name, type.inline, isMutable = true)
                type is SSetTypeIR   -> DataFieldEntry.SetFieldEntry(name, type.inline, isMutable = true)
                type is SSeqTypeIR   -> DataFieldEntry.SequenceFieldEntry(name, type.inline, isMutable = true)
                type is SMapTypeIR   -> DataFieldEntry.MappingFieldEntry(name, type.inline, isMutable = true)
                type is AUnionTypeIR -> DataFieldEntry.UnionFieldEntry(name, type.inline, isMutable = true)
                type.isNullable      -> DataFieldEntry.ObjectFieldEntry(name, type.inline, isMutable = true)
                else                 -> DataFieldEntry.DefaultFieldEntry(name, type.inline, isMutable = true)
            }
        }

        val fieldInvariants = inner.fields.map {
            TypeInvariants.generateInvariant(it.type, it.name.toUpperCamelCase())
        }

        val invariantDeclaration = irNode.inv as AFuncDeclIR?
        val customInvariant = invariantDeclaration?.let { "${it.name.toUpperCamelCase()}(this)" }

        return publicSealedDataClass(recordName, fields, (fieldInvariants + customInvariant).filterNotNull())
        {
            if (invariantDeclaration != null)
                +invariantDeclaration.let { Functions.translate(it) }
        };
    }
}
