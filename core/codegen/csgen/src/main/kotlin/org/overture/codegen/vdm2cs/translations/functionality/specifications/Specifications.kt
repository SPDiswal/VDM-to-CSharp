package org.overture.codegen.vdm2cs.translations.functionality.specifications

import org.overture.codegen.ir.declarations.*
import org.overture.codegen.vdm2cs.common.DefaultNames.FLAT_SPECIFICATION_CLASS
import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.DefinitionTranslationRule
import org.overture.codegen.vdm2cs.translations.functionality.functions.Functions
import org.overture.codegen.vdm2cs.translations.functionality.operations.Operations
import org.overture.codegen.vdm2cs.translations.functionality.states.*
import org.overture.codegen.vdm2cs.translations.functionality.values.Values
import org.overture.codegen.vdm2cs.translations.types.definitions.TypeDefinitions

object Specifications : DefinitionTranslationRule<AModuleDeclIR, CsClassDeclaration>
{
    override fun translate(irNode: AModuleDeclIR): CsClassDeclaration
    {
        val classMembers = mutableListOf<CsDeclaration>()

        for (declaration in irNode.decls)
        {
            when (declaration)
            {
                is ATypeDeclIR   -> classMembers += TypeDefinitions.translate(declaration)

                is AStateDeclIR  ->
                {
                    classMembers += StateTypeDefinitions.translate(declaration)
                    classMembers += States.translate(declaration)
                }

                is AFuncDeclIR   ->
                {
                    classMembers += Functions.translate(declaration)

                    declaration.preCond?.let { classMembers += Functions.translate(it as AFuncDeclIR) }
                    declaration.postCond?.let { classMembers += Functions.translate(it as AFuncDeclIR) }
                }

                is AMethodDeclIR ->
                {
                    classMembers += Operations.translate(declaration)

                    declaration.preCond?.let { classMembers += Functions.translate(it as AFuncDeclIR) }
                    declaration.postCond?.let { classMembers += Functions.translate(it as AFuncDeclIR) }
                }

                is AFieldDeclIR  -> classMembers += Values.translate(declaration)
            }
        }

        return when
        {
            irNode.isFlat -> publicStaticPartialClass(FLAT_SPECIFICATION_CLASS) { +classMembers }
            else          -> publicStaticClass(irNode.name) { +classMembers }
        }
    }
}
