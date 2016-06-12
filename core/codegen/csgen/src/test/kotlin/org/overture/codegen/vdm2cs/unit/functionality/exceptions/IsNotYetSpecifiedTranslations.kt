package org.overture.codegen.vdm2cs.unit.functionality.exceptions

import org.overture.codegen.ir.PIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.functionality.exceptions.IsNotYetSpecified
import org.overture.codegen.vdm2cs.utilities.parseStatement
import org.overture.codegen.vdm2cs.utilities.*

class IsNotYetSpecifiedTranslations : TranslationRuleSpek<PIR, CsStatement>(IsNotYetSpecified)
{
    init
    {
        "is not yet specified expression" describesThat
            isNotYetSpecifiedExpression becomes
            parseStatement("throw new NotImplementedException();")

        "is not yet specified statement" describesThat
            isNotYetSpecifiedStatement becomes
            parseStatement("throw new NotImplementedException();")
    }
}
