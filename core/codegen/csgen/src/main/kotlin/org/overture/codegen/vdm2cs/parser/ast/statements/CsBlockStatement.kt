package org.overture.codegen.vdm2cs.parser.ast.statements

data class CsBlockStatement(val statements: List<CsStatement>): CsStatement
