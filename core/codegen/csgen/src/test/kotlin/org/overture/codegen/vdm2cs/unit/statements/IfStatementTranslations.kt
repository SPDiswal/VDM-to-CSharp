package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.AIfStmIR
import org.overture.codegen.vdm2cs.translations.statements.IfStatements
import org.overture.codegen.vdm2cs.utilities.*

class IfStatementTranslations : StatementTranslationRuleSpek<AIfStmIR>(IfStatements)
{
    init
    {
        "if true then skip" describesThat
            ifStatement(ifCondition = true.toLiteral,
                        thenStatement = skipStatement) becomes
            "if (true) { }"

        "if 1 + 1 = 2 then a := 1; b := 2" describesThat
            ifStatement(ifCondition = (1.toLiteral plus 1.toLiteral) isEqualTo 2.toLiteral,
                        thenStatement = block(
                            assignment(nameDesignator("a", intType), 1.toLiteral),
                            assignment(nameDesignator("b", intType), 2.toLiteral)
                        )) becomes
            "if (1 + 1 == 2) { a = 1; b = 2; }"

        "if true then skip else ship" describesThat
            ifStatement(ifCondition = true.toLiteral,
                        thenStatement = skipStatement,
                        elseStatement = skipStatement) becomes
            "if (true) { } else { }"

        "if 1 + 1 = 2 then a := 1; b := 2 else a := 2; b := 1" describesThat
            ifStatement(ifCondition = (1.toLiteral plus 1.toLiteral) isEqualTo 2.toLiteral,
                        thenStatement = block(
                            assignment(nameDesignator("a", intType), 1.toLiteral),
                            assignment(nameDesignator("b", intType), 2.toLiteral)
                        ),
                        elseStatement = block(
                            assignment(nameDesignator("a", intType), 2.toLiteral),
                            assignment(nameDesignator("b", intType), 1.toLiteral)
                        )) becomes
            "if (1 + 1 == 2) { a = 1; b = 2; } else { a = 2; b = 1; }"

        "if true then if false then return 1 else return 2" describesThat
            ifStatement(ifCondition = true.toLiteral,
                        thenStatement = ifStatement(ifCondition = false.toLiteral,
                                                    thenStatement = returnStatement(1.toLiteral),
                                                    elseStatement = returnStatement(2.toLiteral))) becomes
            "if (true) { if (false) { return 1; } else { return 2; } }"

        "if true then (if false then return 1) else return 2" describesThat
            ifStatement(ifCondition = true.toLiteral,
                        thenStatement = ifStatement(ifCondition = false.toLiteral,
                                                    thenStatement = returnStatement(1.toLiteral)),
                        elseStatement = returnStatement(2.toLiteral)) becomes
            "if (true) { if (false) { return 1; }  } else { return 2; }"

        "if true then return 1 elseif false then return 2 elseif true then return 3 else return 4" describesThat
            ifStatement(ifCondition = true.toLiteral,
                        thenStatement = returnStatement(1.toLiteral),
                        elseIfStatements = listOf(
                            elseIfStatement(ifCondition = false.toLiteral,
                                            thenStatement = returnStatement(2.toLiteral)),
                            elseIfStatement(ifCondition = true.toLiteral,
                                            thenStatement = returnStatement(3.toLiteral))
                        ),
                        elseStatement = returnStatement(4.toLiteral)) becomes
            "if (true) { return 1; } else if (false) { return 2; } else if (true) { return 3; } else { return 4; }"
    }
}
