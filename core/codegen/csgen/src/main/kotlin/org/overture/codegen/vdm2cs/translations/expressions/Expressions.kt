package org.overture.codegen.vdm2cs.translations.expressions

import org.overture.codegen.ir.SExpIR
import org.overture.codegen.ir.expressions.*
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.parser.ast.expressions.CsExpression
import org.overture.codegen.vdm2cs.translations.TranslationRule
import org.overture.codegen.vdm2cs.translations.expressions.`is`.IsExpressions
import org.overture.codegen.vdm2cs.translations.expressions.binary.BinaryExpressions
import org.overture.codegen.vdm2cs.translations.expressions.collections.mappings.MappingExpressions
import org.overture.codegen.vdm2cs.translations.expressions.collections.sequences.*
import org.overture.codegen.vdm2cs.translations.expressions.collections.sets.SetExpressions
import org.overture.codegen.vdm2cs.translations.expressions.creators.*
import org.overture.codegen.vdm2cs.translations.expressions.literals.*
import org.overture.codegen.vdm2cs.translations.expressions.primary.PrimaryExpressions
import org.overture.codegen.vdm2cs.translations.expressions.quantifiers.Quantifiers
import org.overture.codegen.vdm2cs.translations.expressions.scope.*
import org.overture.codegen.vdm2cs.translations.expressions.ternary.TernaryConditions
import org.overture.codegen.vdm2cs.translations.expressions.unary.UnaryExpressions

object Expressions : TranslationRule<SExpIR, CsExpression>
{
    override fun translate(irNode: SExpIR): CsExpression = when (irNode)
    {
    // OPERATORS
        is ATernaryIfExpIR      -> TernaryConditions.translate(irNode)
        is SUnaryExpIR          -> UnaryExpressions.translate(irNode)
        is SBinaryExpIR         -> BinaryExpressions.translate(irNode)
        is SIsExpIR             -> IsExpressions.translate(irNode)

    // LITERALS
        is SLiteralExpIR        -> Literals.translate(irNode)
        is ANullExpIR           -> NullLiterals.translate(irNode)

    // COLLECTIONS
        is SSetExpIR            -> SetExpressions.translate(irNode)
        is SSeqExpIR            -> SequenceExpressions.translate(irNode)
        is ASubSeqExpIR         -> SubSequences.translate(irNode)
        is SMapExpIR            -> MappingExpressions.translate(irNode)

    // CREATORS
        is ANewExpIR            -> RecordCreations.translate(irNode)
        is ATupleExpIR          -> TupleCreations.translate(irNode)
        is AMkBasicExpIR        -> TokenCreations.translate(irNode)

    // QUANTIFIERS
        is SQuantifierExpIR     -> Quantifiers.translate(irNode)

    // SCOPES
        is ALetDefExpIR         -> LetExpressions.translate(irNode)
        is ALetBeStExpIR        -> LetBeSuchThatExpressions.translate(irNode)

    // EXCEPTIONS
        is AUndefinedExpIR,
        is ANotImplementedExpIR -> throw UnsupportedTranslationException(
            "There is no translation for 'not specified yet' or 'undefined' within expressions yet.")

    // PRIMARY EXPRESSIONS
        else                    -> PrimaryExpressions.translate(irNode)
    }
}
