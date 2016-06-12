package org.overture.codegen.vdm2cs.translations.functionality.operations

import org.overture.ast.types.AOperationType
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsMethodDeclaration
import org.overture.codegen.vdm2cs.parser.ast.statements.*
import org.overture.codegen.vdm2cs.parser.builders.publicStaticMethod
import org.overture.codegen.vdm2cs.translations.functionality.FunctionalityTranslationRule
import org.overture.codegen.vdm2cs.translations.functionality.contracts.*
import org.overture.codegen.vdm2cs.translations.statements.Statements
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

object Operations : FunctionalityTranslationRule<AMethodDeclIR>
{
    override fun translate(irNode: AMethodDeclIR): CsMethodDeclaration
    {
        val name = irNode.name.toUpperCamelCase()
        val returnType = irNode.methodType.result.inline
        val parameters = irNode.formalParams.translateParameters
        val typeParameters = irNode.templateTypes.translateTypeParameters

        val body = Statements.translate(irNode.body)
        val precondition = irNode.preCond as AFuncDeclIR?
        val postcondition = irNode.postCond as AFuncDeclIR?

        precondition?.let { Preconditions.applyRemarks(it) }
        postcondition?.let { Postconditions.applyRemarks(it) }

        val parameterInvariants = irNode.formalParams.mapNotNull {
            TypeInvariants.generateInvariant(it.type, it.pattern.inline)
        }

        val resultInvariant = TypeInvariants.generateInvariant(irNode.methodType.result, result(returnType))

        return publicStaticMethod(name, returnType, typeParameters, parameters,
                                  attributes = if (irNode.isPure) listOf("Pure") else emptyList())
        {
            for (parameterInvariant in parameterInvariants)
                +requires(parameterInvariant)

            if (precondition != null)
                +Requires.translate(precondition)

            if (resultInvariant != null)
                +ensures(resultInvariant)

            if (postcondition != null)
                +Ensures.translate(postcondition)

            when
            {
                body is CsReturnStatement && hasNoStatements -> +CsExpressionStatement(body.expression)
                !body.isEmptyBlock                           -> +body.uncoverBlocks
            }
        }
    }

    private val AMethodDeclIR.isPure: Boolean
        get() = let {
            val equivalent = this.methodType.equivalent
            equivalent is AOperationType && equivalent.pure
        }
}
