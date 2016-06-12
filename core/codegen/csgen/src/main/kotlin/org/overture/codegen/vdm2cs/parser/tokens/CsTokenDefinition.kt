package org.overture.codegen.vdm2cs.parser.tokens

import me.sargunvohra.lib.cakeparse.parser.ParsableToken

interface CsTokenDefinition
{
    val pattern: String

    val token: ParsableToken
}
