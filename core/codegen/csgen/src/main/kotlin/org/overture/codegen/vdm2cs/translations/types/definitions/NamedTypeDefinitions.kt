package org.overture.codegen.vdm2cs.translations.types.definitions

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.common.DefaultNames.TYPE_ALIAS_VALUE_FIELD
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsClassDeclaration
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.functions.Functions
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

object NamedTypeDefinitions : TypeDefinitionTranslationRule<ATypeDeclIR>
{
    override fun translate(irNode: ATypeDeclIR): CsClassDeclaration
    {
        val inner = irNode.decl as ANamedTypeDeclIR
        val name = inner.name.name.toUpperCamelCase()
        val type = inner.type

        val field = when
        {
            type.isStringType    -> StringFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
            type is SSetTypeIR   -> SetFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
            type is SSeqTypeIR   -> SequenceFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
            type is SMapTypeIR   -> MappingFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
            type is AUnionTypeIR -> UnionFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
            type.isNullable      -> ObjectFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
            else                 -> DefaultFieldEntry(TYPE_ALIAS_VALUE_FIELD, type.inline)
        }

        val fieldInvariant = TypeInvariants.generateInvariant(type, TYPE_ALIAS_VALUE_FIELD)

        val invariantDeclaration = irNode.inv as AFuncDeclIR?
        val customInvariant = invariantDeclaration?.let { "${it.name.toUpperCamelCase()}(Value)" }

        return publicSealedDataClass(name, listOf(field), listOf(fieldInvariant, customInvariant).filterNotNull())
        {
            if (invariantDeclaration != null)
                +invariantDeclaration.let { Functions.translate(it) }

            +implicitCastOperatorViaInstantiation(type.inline, name)
            +implicitCastOperatorViaValueProperty(name, type.inline)
        };
    }
}
