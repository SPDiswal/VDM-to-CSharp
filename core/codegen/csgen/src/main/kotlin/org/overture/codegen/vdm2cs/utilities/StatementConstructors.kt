package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.AVarDeclIR
import org.overture.codegen.ir.statements.*
import org.overture.codegen.ir.types.AClassTypeIR
import org.overture.codegen.ir.utils.AHeaderLetBeStIR

val skipStatement: ASkipStmIR
    get() = ASkipStmIR()

fun block(vararg statements: SStmIR) = ABlockStmIR().apply {
    this.statements.addAll(statements.map { it.clone() })
}

fun block(declarations: List<AVarDeclIR>, statements: List<SStmIR>) = ABlockStmIR().apply {
    this.localDefs.addAll(declarations.map { it.clone() })
    this.statements.addAll(statements.map { it.clone() })
}

fun atomic(vararg statements: SStmIR) = AAtomicStmIR().apply {
    this.statements.addAll(statements.map { it.clone() })
}

fun assignment(stateDesignator: SStateDesignatorIR, expression: SExpIR) = AAssignmentStmIR().apply {
    this.target = stateDesignator
    this.exp = expression.clone()
}

fun nameDesignator(name: String, type: STypeIR) = AIdentifierStateDesignatorIR().apply {
    this.name = name
    this.isLocal = true
    this.type = type.clone()
}

fun stateFieldNameDesignator(name: String, type: STypeIR) = AIdentifierStateDesignatorIR().apply {
    this.name = name
    this.isLocal = false
    this.type = type.clone()
}

fun fieldDesignator(receiver: SStateDesignatorIR, fieldName: String, type: STypeIR)
    = AFieldStateDesignatorIR().apply {
    this.`object` = receiver
    this.field = fieldName
    this.type = type.clone()
}

fun indexerDesignator(receiver: SStateDesignatorIR, expression: SExpIR, type: STypeIR)
    = AMapSeqStateDesignatorIR().apply {
    this.mapseq = receiver
    this.exp = expression
    this.type = type.clone()
}

fun returnStatement(expression: SExpIR) = AReturnStmIR().apply { this.exp = expression.clone() }

fun call(name: String, resultType: STypeIR, arguments: List<SExpIR>) = APlainCallStmIR().apply {
    this.name = name
    this.args.addAll(arguments.map { it.clone() })
    this.type = resultType.clone()
}

fun letBeSuchThatStatement(binding: SMultipleBindIR, predicate: SExpIR? = null, statement: SStmIR)
    = ALetBeStStmIR().apply {
    this.header = AHeaderLetBeStIR().apply {
        this.binding = binding.clone()
        this.suchThat = predicate?.clone()
    }
    this.statement = statement.clone()
}

fun AClassTypeIR.callWith(name: String, resultType: STypeIR, arguments: List<SExpIR>) = APlainCallStmIR().apply {
    this.classType = this@callWith.clone()
    this.name = name
    this.args.addAll(arguments.map { it.clone() })
    this.type = resultType.clone()
}

fun ifStatement(ifCondition: SExpIR, thenStatement: SStmIR,
                elseIfStatements: List<AElseIfStmIR> = emptyList(), elseStatement: SStmIR? = null) = AIfStmIR().apply {
    this.ifExp = ifCondition.clone()
    this.thenStm = thenStatement.clone()
    this.elseIf.addAll(elseIfStatements.map { it.clone() })
    this.elseStm = elseStatement?.clone()
}

fun elseIfStatement(ifCondition: SExpIR, thenStatement: SStmIR) = AElseIfStmIR().apply {
    this.elseIf = ifCondition.clone()
    this.thenStm = thenStatement.clone()
}

val isNotYetSpecifiedStatement: ANotImplementedStmIR
    get() = ANotImplementedStmIR()

val errorStatement: AErrorStmIR
    get() = AErrorStmIR()
