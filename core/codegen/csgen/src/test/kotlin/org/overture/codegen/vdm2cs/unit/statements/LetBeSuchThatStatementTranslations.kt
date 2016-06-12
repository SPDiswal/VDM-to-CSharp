package org.overture.codegen.vdm2cs.unit.statements

import org.overture.codegen.ir.statements.ALetBeStStmIR
import org.overture.codegen.vdm2cs.translations.statements.LetBeSuchThatStatements
import org.overture.codegen.vdm2cs.utilities.*

class LetBeSuchThatStatementTranslations : StatementTranslationRuleSpek<ALetBeStStmIR>(LetBeSuchThatStatements)
{
    init
    {
        "let a in set { 1, ..., 3 } in skip" describesThat
            letBeSuchThatStatement(binding = setBinding(patterns = listOf(identifierPattern("a")),
                                                        set = setRange(1.toLiteral, 3.toLiteral)),
                                   statement = skipStatement) becomes
            "{" +
            "    var _ = (from a in Enumerable.Range(1, 3).ToHashSet() " +
            "             select new { a }).First();" +
            "    {" +
            "        var a = _.a;" +
            "    }" +
            "}"

        "let a, b in set { 1, ..., 6 } in return a + b" describesThat
            letBeSuchThatStatement(binding = setBinding(patterns = listOf(identifierPattern("a"),
                                                                          identifierPattern("b")),
                                                        set = setRange(1.toLiteral, 6.toLiteral)),
                                   statement = returnStatement(
                                       identifier("a", intType) plus identifier("b", intType))) becomes
            "{" +
            "    var _ = (from a in Enumerable.Range(1, 6).ToHashSet() " +
            "             from b in Enumerable.Range(1, 6).ToHashSet() " +
            "             select new { a, b }).First();" +
            "    {" +
            "        var a = _.a;" +
            "        var b = _.b;" +
            "        return a + b;" +
            "    }" +
            "}"

        "let a in set { 1, ..., 3 } be st a > 1 in skip" describesThat
            letBeSuchThatStatement(binding = setBinding(patterns = listOf(identifierPattern("a")),
                                                        set = setRange(1.toLiteral, 3.toLiteral)),
                                   predicate = identifier("a", intType) isGreaterThan 1.toLiteral,
                                   statement = skipStatement) becomes
            "{" +
            "    var _ = (from a in Enumerable.Range(1, 3).ToHashSet() " +
            "             where a > 1 " +
            "             select new { a }).First();" +
            "    {" +
            "        var a = _.a;" +
            "    }" +
            "}"

        "let a, b in set { 1, ..., 6 } be st a + b > 4 in return a + b" describesThat
            letBeSuchThatStatement(binding = setBinding(patterns = listOf(identifierPattern("a"),
                                                                          identifierPattern("b")),
                                                        set = setRange(1.toLiteral, 6.toLiteral)),
                                   predicate = (identifier("a", intType) plus
                                       identifier("b", intType)) isGreaterThan 4.toLiteral,
                                   statement = returnStatement(
                                       identifier("a", intType) plus identifier("b", intType))) becomes
            "{" +
            "    var _ = (from a in Enumerable.Range(1, 6).ToHashSet() " +
            "             from b in Enumerable.Range(1, 6).ToHashSet() " +
            "             where a + b > 4 " +
            "             select new { a, b }).First();" +
            "    {" +
            "        var a = _.a;" +
            "        var b = _.b;" +
            "        return a + b;" +
            "    }" +
            "}"
    }
}
