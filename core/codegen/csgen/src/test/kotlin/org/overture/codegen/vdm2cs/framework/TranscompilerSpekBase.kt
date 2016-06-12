package org.overture.codegen.vdm2cs.framework

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.framework.utilities.CsDescriber
import org.overture.codegen.vdm2cs.parser.CsFormatter
import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.parser.ast.common.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.*
import kotlin.test.fail

abstract class TranscompilerSpekBase : Spek()
{
    protected fun makeExpectations(expectedCsAst: CsNode, actualCsAst: CsNode): List<TranscompilerExpectation>
    {
        val comparison = compare(listOf(expectedCsAst), listOf(actualCsAst))

        val correctResults = comparison.correctNodes.map {
            val (correct, count) = it
            val actualNodeDescription = CsDescriber.describe(correct.actualNode, article = "the expected")

            TranscompilerExpectation(description = "got $actualNodeDescription" +
                                                   if (count > 1) " ($count times)" else "",

                                     message = "\nIt got $actualNodeDescription:\n\n" +
                                               correct.actualNode.formatAst +
                                               (if (count > 1) "\n\n($count times)" else "") +
                                               "\n\nin\n\n" +
                                               actualCsAst.formatAst,

                                     action = { println(it + "\n") })
        }

        val incorrectResults = comparison.incorrectNodes.map {
            val (incorrect, count) = it
            val expectedNodeDescription = CsDescriber.describe(incorrect.expectedNode)
            val actualNodeDescription = CsDescriber.describe(incorrect.actualNode)

            TranscompilerExpectation(description = "expected $expectedNodeDescription, " +
                                                   "but it got $actualNodeDescription" +
                                                   if (count > 1) " ($count times)" else "",

                                     message = "\nIt expected $expectedNodeDescription:\n\n" +
                                               incorrect.expectedNode.formatAst +
                                               "\n\nHowever, it got $actualNodeDescription:\n\n" +
                                               incorrect.actualNode.formatAst +
                                               (if (count > 1) "\n\n($count times)" else "") +
                                               "\n\nin\n\n" +
                                               actualCsAst.formatAst,

                                     action = { fail(it + "\n") })
        }

        val missingResults = comparison.missingNodes.map {
            val (missing, count) = it
            val expectedNodeDescription = CsDescriber.describe(missing.expectedNode)

            TranscompilerExpectation(description = "missed $expectedNodeDescription" +
                                                   if (count > 1) " ($count times)" else "",

                                     message = "\nIt missed $expectedNodeDescription:\n\n" +
                                               missing.expectedNode.formatAst +
                                               (if (count > 1) "\n\n($count times)" else "") +
                                               "\n\nin\n\n" +
                                               actualCsAst.formatAst,

                                     action = { fail(it + "\n") })
        }

        val unexpectedResults = comparison.unexpectedNodes.map {
            val (unexpected, count) = it
            val actualNodeDescription = CsDescriber.describe(unexpected.actualNode, article = "an unexpected")

            TranscompilerExpectation(description = "got $actualNodeDescription" +
                                                   if (count > 1) " ($count times)" else "",

                                     message = "\nIt got $actualNodeDescription:\n\n" +
                                               unexpected.actualNode.formatAst +
                                               (if (count > 1) "\n\n($count times)" else "") +
                                               "\n\nin\n\n" +
                                               actualCsAst.formatAst,

                                     action = { fail(it + "\n") })
        }

        val combinedResults = correctResults + incorrectResults + missingResults + unexpectedResults

        return when
        {
            combinedResults.isNotEmpty() -> combinedResults
            else                         ->
            {
                val expectedNodeDescription = CsDescriber.describe(expectedCsAst)

                listOf(TranscompilerExpectation(description = "got the expected $expectedNodeDescription",

                                                message = "It got the expected $expectedNodeDescription:\n\n" +
                                                          expectedCsAst.formatAst +
                                                          "\n\nin\n\n" +
                                                          actualCsAst.formatAst,

                                                action = { println(it + "\n") }))
            }
        }
    }
    
    private fun compare(expectedNodes: List<CsNode>, actualNodes: List<CsNode>): Comparison
    {
        val correctNodes = emptyMutableMultiSetOf<Correct>()
        val incorrectNodes = emptyMutableMultiSetOf<Incorrect>()
        val missingNodes = emptyMutableMultiSetOf<Missing>()
        val unexpectedNodes = emptyMutableMultiSetOf<Unexpected>()

        val remainingActualNodes = actualNodes.toMutableList()

        for (expectedNode in expectedNodes)
        {
            val resemblance = remainingActualNodes
                .map { expectedNode resemblanceTo it }
                .filter { it.score > 12 }
                .maxBy { it.score }

            if (resemblance != null)
            {
                val actualNode = resemblance.actualNode
                remainingActualNodes.remove(actualNode)

                if (actualNode hasSameSignatureAs expectedNode) correctNodes += Correct(actualNode)
                else incorrectNodes += Incorrect(expectedNode, actualNode)

                val resultsOfChildren = compare(expectedNode.significantChildren, actualNode.significantChildren)

                correctNodes += resultsOfChildren.correctNodes
                incorrectNodes += resultsOfChildren.incorrectNodes
                missingNodes += resultsOfChildren.missingNodes
                unexpectedNodes += resultsOfChildren.unexpectedNodes
            }
            else missingNodes += Missing(expectedNode)
        }

        unexpectedNodes += remainingActualNodes.map { Unexpected(it) }.toMultiSet()
        return Comparison(correctNodes, incorrectNodes, missingNodes, unexpectedNodes)
    }

    private fun <T> Iterable<T>.toMultiSet()
        = this.groupBy({ it }).mapValues { it.value.count() }

    private fun <T> emptyMutableMultiSetOf(): MutableMap<T, Int> = mutableMapOf()
    
    private operator fun <T> MutableMap<T, Int>.plusAssign(element: T)
    {
        this[element] = (this[element] ?: 0) + 1
    }

    private operator fun <T> MutableMap<T, Int>.plusAssign(elements: Map<T, Int>)
    {
        for ((element, count) in elements)
            this[element] = (this[element] ?: 0) + count
    }
    
    private infix fun CsNode.resemblanceTo(actualNode: CsNode): Resemblance
    {
        var resemblanceScore = 0

        when
        {
            this is CsUsingDirective && actualNode is CsUsingDirective             ->
            {
                resemblanceScore += 1
                if (importedType == actualNode.importedType) resemblanceScore += 12
                if (isStatic == actualNode.isStatic) resemblanceScore += 1
            }

            this is CsNamespaceDeclaration && actualNode is CsNamespaceDeclaration ->
            {
                resemblanceScore += 1
                if (name == actualNode.name) resemblanceScore += 12
                if (members == actualNode.members) resemblanceScore += 12
            }

            this is CsClassDeclaration && actualNode is CsClassDeclaration         ->
            {
                resemblanceScore += 1
                if (name == actualNode.name) resemblanceScore += 12
                if (modifiers.isNotEmpty() && modifiers == actualNode.modifiers) resemblanceScore += 6
                if (typeParameters?.isNotEmpty() ?: false && typeParameters == actualNode.typeParameters) resemblanceScore += 2
                if (superTypes.isNotEmpty() && superTypes == actualNode.superTypes) resemblanceScore += 6
                if (attributes.isNotEmpty() && attributes == actualNode.attributes) resemblanceScore += 1
                if (members == actualNode.members) resemblanceScore += 12
            }

            this is CsMethodDeclaration && actualNode is CsMethodDeclaration       ->
            {
                resemblanceScore += 1
                if (name == actualNode.name) resemblanceScore += 12
                if (returnType == actualNode.returnType) resemblanceScore += 4
                if (parameters == actualNode.parameters) resemblanceScore += 4
                if (modifiers.isNotEmpty() && modifiers == actualNode.modifiers) resemblanceScore += 4
                if (typeParameters?.isNotEmpty() ?: false && typeParameters == actualNode.typeParameters) resemblanceScore += 2
                if (attributes.isNotEmpty() && attributes == actualNode.attributes) resemblanceScore += 2
                if (statement == actualNode.statement) resemblanceScore += 12
            }

            this is CsPropertyDeclaration && actualNode is CsPropertyDeclaration   ->
            {
                resemblanceScore += 1
                if (name == actualNode.name) resemblanceScore += 12
                if (type == actualNode.type) resemblanceScore += 6
                if (modifiers.isNotEmpty() && modifiers == actualNode.modifiers) resemblanceScore += 4
                if (attributes.isNotEmpty() && attributes == actualNode.attributes) resemblanceScore += 1
                if (getter == actualNode.getter) resemblanceScore += 1
                if (setter == actualNode.setter) resemblanceScore += 1
                if (initialiser != null && initialiser == actualNode.initialiser) resemblanceScore += 6
            }

            this.javaClass.kotlin == actualNode.javaClass.kotlin                   -> resemblanceScore += 13
        }

        return Resemblance(actualNode, resemblanceScore)
    }

    private val CsNode.significantChildren: List<CsNode>
        get() = when (this)
        {
            is CsDocument             -> usingDirectives + namespace
            is CsNamespaceDeclaration -> members
            is CsClassDeclaration     -> members
            else                      -> emptyList()
        }

    private infix fun CsNode.hasSameSignatureAs(that: CsNode) = when
    {
        this is CsDocument &&
        that is CsDocument             -> true

        this is CsUsingDirective &&
        that is CsUsingDirective       -> importedType == that.importedType && isStatic == that.isStatic

        this is CsNamespaceDeclaration &&
        that is CsNamespaceDeclaration -> name == that.name

        this is CsClassDeclaration &&
        that is CsClassDeclaration     -> name == that.name &&
                                          modifiers == that.modifiers &&
                                          typeParameters == that.typeParameters &&
                                          superTypes == that.superTypes &&
                                          attributes == that.attributes

        else                           -> this == that
    }

    private val CsNode.formatAst: String
        get() = CsFormatter.format(this).prependIndent("    ").trimEnd()

    private data class Resemblance(val actualNode: CsNode, val score: Int)

    private data class Correct(val actualNode: CsNode)

    private data class Incorrect(val expectedNode: CsNode, val actualNode: CsNode)

    private data class Missing(val expectedNode: CsNode)

    private data class Unexpected(val actualNode: CsNode)

    private data class Comparison(val correctNodes: Map<Correct, Int>,
                                  val incorrectNodes: Map<Incorrect, Int>,
                                  val missingNodes: Map<Missing, Int>,
                                  val unexpectedNodes: Map<Unexpected, Int>)

    private val placeholders: List<String>
        get() = listOf("Alpha", "Bravo", "Charlie", "Delta",
                       "Echo", "Foxtrot", "Golf", "Hotel",
                       "India", "Juliet", "Kilo", "Lima",
                       "Mike", "November", "Oscar", "Papa",
                       "Quebec", "Romeo", "Sierra", "Tango",
                       "Uniform", "Victor", "Whiskey", "Xray",
                       "Yankee", "Zulu")

    private var placeholderIndex = -1

    val nextPlaceholder: String
        get()
        {
            placeholderIndex = (placeholderIndex + 1) % placeholders.size
            return placeholders[placeholderIndex]
        }

    val nextLowerCasePlaceholder: String
        get() = nextPlaceholder.decapitalize()

    val placeholder: String
        get() = placeholders[placeholderIndex]

    val lowerCasePlaceholder: String
        get() = placeholders[placeholderIndex].decapitalize()
}
