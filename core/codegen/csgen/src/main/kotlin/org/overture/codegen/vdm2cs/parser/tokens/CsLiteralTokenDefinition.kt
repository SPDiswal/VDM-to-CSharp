package org.overture.codegen.vdm2cs.parser.tokens

import me.sargunvohra.lib.cakeparse.api.token
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

enum class CsLiteralTokenDefinition(override val pattern: String) : CsTokenDefinition
{
    DECIMAL_LITERAL("[0-9]+\\.?[0-9]*m"),
    INTEGER_LITERAL("[0-9]+"),
    CHARACTER_LITERAL("'(?:[^\\\\']|\\\\.)'"),
    STRING_LITERAL("\"(?:[^\\\\\"]|\\\\.)*\"");

    override val token = token(name.toUpperCamelCase(), pattern)

    companion object
    {
        val tokenDefinitions: List<CsTokenDefinition>
            get() = values().toList()
    }
}
