package org.overture.codegen.vdm2cs.parser.tokens

import me.sargunvohra.lib.cakeparse.api.token
import org.overture.codegen.vdm2cs.parser.CsParser
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

enum class CsIdentifierTokenDefinition(override val pattern: String) : CsTokenDefinition
{
    IDENTIFIER("(?!${CsParser.reservedWords.map { """$it\b""" }.joinToString("|")})[a-zA-Z_][a-zA-Z0-9_]*");

    override val token = token(name.toUpperCamelCase(), pattern)

    companion object
    {
        val tokenDefinitions: List<CsTokenDefinition>
            get() = values().toList()
    }
}
