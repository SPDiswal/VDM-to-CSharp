package org.overture.codegen.vdm2cs.unit.functionality.exceptions

import org.overture.codegen.ir.PIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.functionality.exceptions.*
import org.overture.codegen.vdm2cs.utilities.parseStatement
import org.overture.codegen.vdm2cs.utilities.*

class ErrorStatementTranslations : TranslationRuleSpek<PIR, CsStatement>(ErrorStatements)
{
    init
    {
        "error statement" describesThat
            errorStatement becomes
            parseStatement("throw new Exception();")
    }
}
