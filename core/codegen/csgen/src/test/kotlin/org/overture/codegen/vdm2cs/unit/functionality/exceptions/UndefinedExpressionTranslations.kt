package org.overture.codegen.vdm2cs.unit.functionality.exceptions

import org.overture.codegen.ir.PIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.functionality.exceptions.*
import org.overture.codegen.vdm2cs.utilities.parseStatement
import org.overture.codegen.vdm2cs.utilities.*

class UndefinedExpressionTranslations : TranslationRuleSpek<PIR, CsStatement>(UndefinedExpressions)
{
    init
    {
        "undefined expression" describesThat
            undefinedExpression becomes
            parseStatement("throw new Exception();")
    }
}
