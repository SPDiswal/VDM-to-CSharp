package org.overture.codegen.vdm2cs.parser.tokens

import me.sargunvohra.lib.cakeparse.api.token
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

enum class CsWhitespaceTokenDefinition(override val pattern: String) : CsTokenDefinition
{
    NEWLINE("\\R"),
    SPACE("[ \\t]");

    override val token = token(name.toUpperCamelCase(), pattern, ignore = true)

    companion object
    {
        val tokenDefinitions: List<CsTokenDefinition>
            get() = values().toList()
    }
}
