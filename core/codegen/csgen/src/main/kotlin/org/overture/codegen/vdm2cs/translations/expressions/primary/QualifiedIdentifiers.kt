package org.overture.codegen.vdm2cs.translations.expressions.primary

import org.overture.codegen.ir.expressions.AExplicitVarExpIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.parser.ast.expressions.primary.CsMemberExpression
import org.overture.codegen.vdm2cs.translations.*
import org.overture.codegen.vdm2cs.translations.expressions.ExpressionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object QualifiedIdentifiers : ExpressionTranslationRule<AExplicitVarExpIR>
{
    override fun translate(irNode: AExplicitVarExpIR) = let {
        val qualifier = (irNode.classType as AClassTypeIR).name
        val name = when (irNode.type)
        {
            is AMethodTypeIR -> irNode.name.toUpperCamelCase()
            else             -> irNode.name.toLowerCamelCase()
        }

        CsMemberExpression(parseExpression(qualifier), parseName(name))
    }
}
