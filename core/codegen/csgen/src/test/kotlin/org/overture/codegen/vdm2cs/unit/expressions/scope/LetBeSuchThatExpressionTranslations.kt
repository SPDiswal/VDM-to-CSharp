package org.overture.codegen.vdm2cs.unit.expressions.scope

import org.overture.codegen.ir.expressions.ALetBeStExpIR
import org.overture.codegen.vdm2cs.translations.expressions.scope.LetBeSuchThatExpressions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class LetBeSuchThatExpressionTranslations : ExpressionTranslationRuleSpek<ALetBeStExpIR>(LetBeSuchThatExpressions)
{
    init
    {
        "let a in set { 1, ..., 3 } in true" describesThat
            letBeSuchThat(binding = setBinding(patterns = listOf(identifierPattern("a")),
                                               set = setRange(1.toLiteral, 3.toLiteral)),
                          expression = true.toLiteral) becomes
            "Let(() => {" +
            "    var _ = (from a in Enumerable.Range(1, 3).ToHashSet() " +
            "             select new { a }).First();" +
            "    {" +
            "        var a = _.a;" +
            "        return true;" +
            "    }" +
            "})"

        "let a, b in set { 1, ..., 6 } in a + b" describesThat
            letBeSuchThat(binding = setBinding(patterns = listOf(identifierPattern("a"),
                                                                 identifierPattern("b")),
                                               set = setRange(1.toLiteral, 6.toLiteral)),
                          expression = identifier("a", intType) plus identifier("b", intType)) becomes
            "Let(() => {" +
            "    var _ = (from a in Enumerable.Range(1, 6).ToHashSet() " +
            "             from b in Enumerable.Range(1, 6).ToHashSet() " +
            "             select new { a, b }).First();" +
            "    {" +
            "        var a = _.a;" +
            "        var b = _.b;" +
            "        return a + b;" +
            "    }" +
            "})"

        "let a in set { 1, ..., 3 } be st a > 1 in true" describesThat
            letBeSuchThat(binding = setBinding(patterns = listOf(identifierPattern("a")),
                                               set = setRange(1.toLiteral, 3.toLiteral)),
                          predicate = identifier("a", intType) isGreaterThan 1.toLiteral,
                          expression = true.toLiteral) becomes
            "Let(() => {" +
            "    var _ = (from a in Enumerable.Range(1, 3).ToHashSet() " +
            "             where a > 1 " +
            "             select new { a }).First();" +
            "    {" +
            "        var a = _.a;" +
            "        return true;" +
            "    }" +
            "})"

        "let a, b in set { 1, ..., 6 } be st a + b > 4 in a + b" describesThat
            letBeSuchThat(binding = setBinding(patterns = listOf(identifierPattern("a"),
                                                                 identifierPattern("b")),
                                               set = setRange(1.toLiteral, 6.toLiteral)),
                          predicate = (identifier("a", intType) plus
                              identifier("b", intType)) isGreaterThan 4.toLiteral,
                          expression = identifier("a", intType) plus identifier("b", intType)) becomes
            "Let(() => {" +
            "    var _ = (from a in Enumerable.Range(1, 6).ToHashSet() " +
            "             from b in Enumerable.Range(1, 6).ToHashSet() " +
            "             where a + b > 4 " +
            "             select new { a, b }).First();" +
            "    {" +
            "        var a = _.a;" +
            "        var b = _.b;" +
            "        return a + b;" +
            "    }" +
            "})"
    }
}
