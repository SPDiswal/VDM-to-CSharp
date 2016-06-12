package org.overture.codegen.vdm2cs.translations.statements

import org.overture.codegen.ir.SStmIR
import org.overture.codegen.vdm2cs.parser.ast.statements.CsStatement
import org.overture.codegen.vdm2cs.translations.TranslationRule

interface StatementTranslationRule<TIrNode : SStmIR> : TranslationRule<TIrNode, CsStatement>
