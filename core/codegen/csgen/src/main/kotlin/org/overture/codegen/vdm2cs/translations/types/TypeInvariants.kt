package org.overture.codegen.vdm2cs.translations.types

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.types.*
import org.overture.codegen.vdm2cs.utilities.*

/**
 * This translation generates the following type invariants:
 * - Bounds-checking for 'nat' and 'nat1'.
 * - Null-checking for non-optional nullable types and optional types in general.
 * - Consistency-checking for 'seq1' and 'inmap'.
 * - Element consistency-checking for 'set', 'seq', 'seq1', 'map', 'inmap', product types and union types.
 */
object TypeInvariants
{
    fun generateInvariant(type: STypeIR, expression: String, skipNullChecking: Boolean = false): String?
    {
        val conjuncts = mutableListOf<String?>()

        if (type.isNullable && !type.isOptional && type !is AUnionTypeIR && !skipNullChecking)
            conjuncts += "$expression != null"

        when (type)
        {
            is ANatNumericBasicTypeIR  -> conjuncts += "$expression >= 0"

            is ANat1NumericBasicTypeIR -> conjuncts += "$expression > 0"

            is ASetSetTypeIR           -> conjuncts += elementInvariant(expression, type.setOf)

            is ASeqSeqTypeIR           ->
            {
                if (type.isNonEmpty) conjuncts += "$expression.Any()"
                conjuncts += elementInvariant(expression, type.seqOf)
            }

            is AMapMapTypeIR           ->
            {
                if (type.isInjective) conjuncts += "$expression.IsInjective()"
                conjuncts += elementInvariant("$expression.Keys", type.from)
                conjuncts += elementInvariant("$expression.Values", type.to)
            }

            is AQuoteTypeIR            -> conjuncts += "$expression == Quote.${type.value}"

            is ATupleTypeIR            ->
                conjuncts += type.types.mapIndexed { i, t -> generateInvariant(t, "$expression.Item${i + 1}") }

            is AUnionTypeIR            ->
            {
                val nonQuoteComponents = type.types.filterNot { it is AQuoteTypeIR }.map {
                    val elementTypeCheck = if (it !is AUnionTypeIR) "$expression is ${it.inline}" else null
                    val elementInvariant = generateInvariant(it, expression, skipNullChecking = true)
                    joinInvariantConjuncts(listOf(elementTypeCheck, elementInvariant))
                }

                val quoteInvariants = type.types.filter { it is AQuoteTypeIR }.map { generateInvariant(it, expression) }
                val joinedQuoteInvariants = quoteInvariants.joinToString(" || ")
                val quoteComponent = when (quoteInvariants.isNotEmpty())
                {
                    true  -> when
                    {
                        type.isNonOptionalUnionOfQuotes -> "($joinedQuoteInvariants)"
                        type.isOptionalUnionOfQuotes    -> "$joinedQuoteInvariants"
                        else                            -> "$expression is Quote && ($joinedQuoteInvariants)"
                    }
                    false -> null
                }

                val unionInvariant = (nonQuoteComponents + quoteComponent).filterNotNull().joinToString(" || ")

                if (unionInvariant.isNotEmpty())
                    conjuncts += unionInvariant
            }
        }

        val resultingInvariant = joinInvariantConjuncts(conjuncts)

        return when
        {
            type.isNamedType && type.isOptional                       -> null
            type.isNamedType && !type.isOptional && !skipNullChecking -> "$expression != null"
            type.isOptional && resultingInvariant != null             -> "($expression == null || $resultingInvariant)"
            else                                                      -> resultingInvariant
        }
    }

    private fun elementInvariant(expression: String, elementType: STypeIR): String?
    {
        val elementIdentifier = generateElementIdentifier(expression)
        val elementInvariant = generateInvariant(elementType, elementIdentifier)
        return elementInvariant?.let { forAll(expression, elementIdentifier, it) }
    }

    private fun generateElementIdentifier(collectionExpression: String) = when
    {
        collectionExpression.startsWith("_") -> "_${collectionExpression.takeWhile { it == '_' }}"
        else                                 -> "_"
    }

    private fun joinInvariantConjuncts(conjuncts: List<String?>): String?
    {
        val resultingInvariant = conjuncts.filterNotNull().joinToString(" && ")
        return if (resultingInvariant.isNotEmpty()) resultingInvariant else null
    }
}
