package org.overture.codegen.vdm2cs.unit.expressions.`is`

import org.overture.codegen.ir.expressions.AGeneralIsExpIR
import org.overture.codegen.vdm2cs.translations.expressions.`is`.CommonIsExpressions
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class CommonIsTranslations : ExpressionTranslationRuleSpek<AGeneralIsExpIR>(CommonIsExpressions)
{
    init
    {
        "is_(\"ABC\", seq of char)" describesThat
            isExpression("ABC".toLiteral, seqOf(charType)) becomes
            "\"ABC\" is string"

        "is_({ 1 }, set of nat)" describesThat
            isExpression(setEnumeration(1.toLiteral), setOf(natType)) becomes
            "Let(() => {" +
            "    var _ = new HashSet<int> { 1 } as HashSet<int>;" +
            "    return _ != null && ${forAll("_", "__ >= 0")};" +
            "})"

        "is_([ 1 ], seq of nat)" describesThat
            isExpression(sequenceEnumeration(1.toLiteral), seqOf(natType)) becomes
            "Let(() => {" +
            "    var _ = new List<int> { 1 } as List<int>;" +
            "    return _ != null && ${forAll("_", "__ >= 0")};" +
            "})"

        "is_([ 1, 2 ], seq1 of nat)" describesThat
            isExpression(sequenceEnumeration(1.toLiteral, 2.toLiteral), seq1Of(natType)) becomes
            "Let(() => {" +
            "    var _ = new List<int> { 1, 2 } as List<int>;" +
            "    return _ != null && _.Any() && ${forAll("_", "__ >= 0")};" +
            "})"

        "is_({ 1 |-> 2 }, map nat to nat)" describesThat
            isExpression(mappingEnumeration(1.toLiteral to 2.toLiteral), map(natType to natType)) becomes
            "Let(() => {" +
            "    var _ = new Dictionary<int, int> { [1] = 2 } as Dictionary<int, int>;" +
            "    return _ != null && ${forAll("_.Keys", "__ >= 0")} && ${forAll("_.Values", "__ >= 0")};" +
            "})"

        "is_({ 1 |-> 2 }, inmap nat to nat)" describesThat
            isExpression(mappingEnumeration(1.toLiteral to 2.toLiteral), inmap(natType to natType)) becomes
            "Let(() => {" +
            "    var _ = new Dictionary<int, int> { [1] = 2 } as Dictionary<int, int>;" +
            "    return _ != null && _.IsInjective() && " +
            "           ${forAll("_.Keys", "__ >= 0")} && ${forAll("_.Values", "__ >= 0")};" +
            "})"

        "is_(<$nextPlaceholder>, <$placeholder>)" describesThat
            isExpression(quote(placeholder), quoteType(placeholder)) becomes
            "(Quote.$placeholder as Quote?) == Quote.$placeholder"

        "is_(<$nextPlaceholder>, <$placeholder>)" describesThat
            isExpression(quote(placeholder), quoteType(placeholder)) becomes
            "(Quote.$placeholder as Quote?) == Quote.$placeholder"

        "is_$nextPlaceholder(a)" describesThat
            isExpression(identifier("a", recordType(name = placeholder)), recordType(name = placeholder)) becomes
            "a is $placeholder"

        "is_$nextPlaceholder(b)" describesThat
            isExpression(identifier("b", recordType(name = placeholder)), recordType(name = placeholder)) becomes
            "b is $placeholder"

        "is_$nextPlaceholder(c)" describesThat
            isExpression(identifier("c", namedType(name = placeholder, type = boolType)),
                         namedType(name = placeholder, type = boolType)) becomes
            "c is $placeholder"

        "is_$nextPlaceholder(d)" describesThat
            isExpression(identifier("d", namedType(name = placeholder, type = natType)),
                         namedType(name = placeholder, type = natType)) becomes
            "d is $placeholder"
    }
}
