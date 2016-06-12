package org.overture.codegen.vdm2cs.parser.tokens

import me.sargunvohra.lib.cakeparse.api.token
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

enum class CsPunctuationTokenDefinition(override val pattern: String) : CsTokenDefinition
{
    // Multi-character punctuation.
    AND("&&"),
    EQUAL_TO("=="),
    GOES_TO("=>"),
    GREATER_THAN_OR_EQUAL_TO(">="),
    LESS_THAN_OR_EQUAL_TO("<="),
    NOT_EQUAL_TO("!="),
    OR("\\|\\|"),

    // Single-character punctuation.
    ASSIGNMENT("="),
    COLON(":"),
    COMMA(","),
    DOT("\\."),
    EXCLAMATION_MARK("\\!"),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    LEFT_BRACE("\\{"),
    LEFT_BRACKET("\\["),
    LEFT_PARENTHESIS("\\("),
    MINUS("\\-"),
    MODULUS("%"),
    PLUS("\\+"),
    QUESTION_MARK("\\?"),
    RIGHT_BRACE("\\}"),
    RIGHT_BRACKET("\\]"),
    RIGHT_PARENTHESIS("\\)"),
    SEMICOLON(";"),
    DIVIDE("/"),
    TIMES("\\*"),
    VERBATIM("@"),
    XOR("\\^");

    override val token = token(name.toUpperCamelCase(), pattern)

    companion object
    {
        val tokenDefinitions: List<CsTokenDefinition>
            get() = values().toList()
    }
}
