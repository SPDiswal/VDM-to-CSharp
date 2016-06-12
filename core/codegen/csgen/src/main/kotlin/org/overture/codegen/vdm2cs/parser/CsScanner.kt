package org.overture.codegen.vdm2cs.parser

import me.sargunvohra.lib.cakeparse.lexer.TokenInstance
import org.overture.codegen.vdm2cs.parser.tokens.*

object CsScanner
{
    private val tokens = CsIdentifierTokenDefinition.tokenDefinitions +
                         CsLiteralTokenDefinition.tokenDefinitions +
                         CsPunctuationTokenDefinition.tokenDefinitions +
                         CsReservedWordTokenDefinition.tokenDefinitions +
                         CsWhitespaceTokenDefinition.tokenDefinitions

    private val tokeniser = "${tokens.map { "(${it.pattern})" }.joinToString("|")}".toRegex()

    fun scan(input: String) = tokeniser.findAll(input)
        .map { TokenInstance(tokens[it.firstMatchingGroupIndex].token, it.value, 0, 0, 0) }
        .filter { !it.type.ignore }

    private val MatchResult.firstMatchingGroupIndex: Int
        get() = groups.drop(1).indexOfFirst { it != null }
}
