package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.SStmIR
import org.overture.codegen.vdm2cs.framework.TranslationRuleSpek
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.utilities.parseStatement
import org.overture.codegen.vdm2cs.translations.statements.StatementTranslationRule

abstract class StatementTranslationRuleSpek<TIrNode : SStmIR>(translationRule: StatementTranslationRule<TIrNode>) :
    TranslationRuleSpek<TIrNode, CsStatement>(translationRule)
{
    infix fun TranslationDescription<TIrNode>.becomes(csStatementSyntax: String)
        = this becomes parseStatement(csStatementSyntax)
}
