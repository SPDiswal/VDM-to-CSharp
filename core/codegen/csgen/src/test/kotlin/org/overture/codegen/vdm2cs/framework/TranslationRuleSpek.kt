package org.overture.codegen.vdm2cs.framework

import org.overture.codegen.ir.PIR
import org.overture.codegen.vdm2cs.common.UnsupportedTranslationException
import org.overture.codegen.vdm2cs.parser.ast.CsNode
import org.overture.codegen.vdm2cs.translations.TranslationRule
import kotlin.test.fail

abstract class TranslationRuleSpek<TIrNode : PIR, TCsNode : CsNode>(
    private val translationRule: TranslationRule<TIrNode, TCsNode>) : TranscompilerSpekBase()
{
    data class TranslationDescription<TIrNode : PIR>(val description: String, val irNode: TIrNode)

    infix fun String.describesThat(givenIrNode: TIrNode) = TranslationDescription(this, givenIrNode)

    infix fun TranslationDescription<TIrNode>.becomes(expectedCsAst: TCsNode)
    {
        val (inputDescription, irNode) = this

        given("an IR node of '$inputDescription'")
        {
            on("translation via the rule '${translationRule.javaClass.kotlin.simpleName}'")
            {
                val actualCsAst = translationRule.translate(irNode)
                val expectations = makeExpectations(expectedCsAst, actualCsAst)

                for ((expectationDescription, message, action) in expectations)
                    it(expectationDescription) { action(message) }
            }
        }
    }

    infix fun TranslationDescription<TIrNode>.throws(message: String)
    {
        val (description, irNode) = this
        val exceptionDescription = "UnsupportedTranslationException with the message"
        val preamble = "It expected an $exceptionDescription '$message' to be thrown,\nbut got"

        given("an IR node of '$description'")
        {
            on("translation via the rule '${translationRule.javaClass.simpleName}'")
            {
                it("throws an $exceptionDescription '$message'")
                {
                    try
                    {
                        translationRule.translate(irNode)
                        fail("\n$preamble no exceptions.")
                    }
                    catch (e: UnsupportedTranslationException)
                    {
                        if (e.message != message)
                            fail("\n$preamble an $exceptionDescription '${e.message}'.")
                        else
                            println("\nIt threw the expected $exceptionDescription '${e.message}'.")
                    }
                    catch (e: Exception)
                    {
                        fail("\n$preamble ${e.javaClass.simpleName}.")
                    }
                }
            }
        }
    }
}
