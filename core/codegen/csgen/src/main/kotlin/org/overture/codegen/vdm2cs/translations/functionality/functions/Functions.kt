package org.overture.codegen.vdm2cs.translations.functionality.functions

import org.overture.codegen.ir.declarations.AFuncDeclIR
import org.overture.codegen.ir.expressions.ANotImplementedExpIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsMethodDeclaration
import org.overture.codegen.vdm2cs.parser.builders.purePublicStaticMethod
import org.overture.codegen.vdm2cs.translations.functionality.FunctionalityTranslationRule
import org.overture.codegen.vdm2cs.translations.functionality.contracts.*
import org.overture.codegen.vdm2cs.translations.types.TypeInvariants
import org.overture.codegen.vdm2cs.utilities.*

object Functions : FunctionalityTranslationRule<AFuncDeclIR>
{
    override fun translate(irNode: AFuncDeclIR): CsMethodDeclaration
    {
        val name = irNode.name.toUpperCamelCase()
        val returnType = irNode.methodType.result.inline
        val typeParameters = irNode.templateTypes.translateTypeParameters
        val parameters = irNode.formalParams.translateParameters

        val body = irNode.body
        val precondition = irNode.preCond as AFuncDeclIR?
        val postcondition = irNode.postCond as AFuncDeclIR?

        precondition?.let { Preconditions.applyRemarks(it) }
        postcondition?.let { Postconditions.applyRemarks(it) }

        val parameterInvariants = irNode.formalParams.mapNotNull {
            TypeInvariants.generateInvariant(it.type, it.pattern.inline)
        }

        val resultInvariant = TypeInvariants.generateInvariant(irNode.methodType.result, result(returnType))

        return purePublicStaticMethod(name, returnType, typeParameters, parameters)
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
                body is ANotImplementedExpIR -> +throwsNotImplementedException()
                hasNoStatements              -> +expressionBody(body.inline)
                else                         -> +returns(body.inline)
            }
        }
    }
}
