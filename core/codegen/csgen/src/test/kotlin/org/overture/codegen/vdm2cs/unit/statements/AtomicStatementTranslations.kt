package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.AAtomicStmIR
import org.overture.codegen.vdm2cs.translations.statements.AtomicStatements
import org.overture.codegen.vdm2cs.utilities.*

class AtomicStatementTranslations : StatementTranslationRuleSpek<AAtomicStmIR>(AtomicStatements)
{
    val recordType = recordType(name = "Record")

    init
    {
        "atomic (a := 1; b := 2)" describesThat
            atomic(assignment(stateDesignator = nameDesignator("a", intType), expression = 1.toLiteral),
                   assignment(stateDesignator = nameDesignator("b", intType), expression = 2.toLiteral)) becomes
            "Atomic(() => {" +
            "    a = 1;" +
            "    b = 2;" +
            "});"

        "atomic (x.first := 3; x.second := 4; x.third := 5)" describesThat
            atomic(assignment(stateDesignator = fieldDesignator(nameDesignator("x", recordType), "first", intType),
                              expression = 3.toLiteral),
                   assignment(stateDesignator = fieldDesignator(nameDesignator("x", recordType), "second", intType),
                              expression = 4.toLiteral),
                   assignment(stateDesignator = fieldDesignator(nameDesignator("x", recordType), "third", intType),
                              expression = 5.toLiteral)) becomes
            "Atomic(() => {" +
            "    x.First = 3;" +
            "    x.Second = 4;" +
            "    x.Third = 5;" +
            "});"
    }
}
