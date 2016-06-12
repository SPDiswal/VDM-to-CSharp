package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.vdm2cs.translations.statements.BlockStatements
import org.overture.codegen.vdm2cs.utilities.*

class BlockStatementTranslations : StatementTranslationRuleSpek<ABlockStmIR>(BlockStatements)
{
    val recordType = recordType(name = "Record")

    init
    {
        "a := 1; b := 2" describesThat
            block(assignment(stateDesignator = nameDesignator("a", intType), expression = 1.toLiteral),
                  assignment(stateDesignator = nameDesignator("b", intType), expression = 2.toLiteral)) becomes
            "{" +
            "    a = 1;" +
            "    b = 2;" +
            "}"

        "x.first := 3; x.second := 4; x.third := 5" describesThat
            block(assignment(stateDesignator = fieldDesignator(nameDesignator("x", recordType), "first", intType),
                             expression = 3.toLiteral),
                  assignment(stateDesignator = fieldDesignator(nameDesignator("x", recordType), "second", intType),
                             expression = 4.toLiteral),
                  assignment(stateDesignator = fieldDesignator(nameDesignator("x", recordType), "third", intType),
                             expression = 5.toLiteral)) becomes
            "{" +
            "    x.First = 3;" +
            "    x.Second = 4;" +
            "    x.Third = 5;" +
            "}"

        "let x = 1 in a := 1" describesThat
            block(
                declarations = listOf(
                    variable("x", intType, 1.toLiteral)
                ),
                statements = listOf(
                    assignment(stateDesignator = nameDesignator("a", intType), expression = 1.toLiteral)
                )) becomes
            "{" +
            "    var x = 1;" +
            "    a = 1;" +
            "}"

        "let x = 1, y = 2 in a := x; b := y" describesThat
            block(
                declarations = listOf(
                    variable("x", intType, 1.toLiteral),
                    variable("y", intType, 2.toLiteral)
                ),
                statements = listOf(
                    assignment(stateDesignator = stateFieldNameDesignator("a", intType),
                               expression = identifier("x", intType)),
                    assignment(stateDesignator = stateFieldNameDesignator("b", intType),
                               expression = identifier("y", intType))
                )) becomes
            "{" +
            "    var x = 1;" +
            "    var y = 2;" +
            "    State.A = x;" +
            "    State.B = y;" +
            "}"
    }
}
