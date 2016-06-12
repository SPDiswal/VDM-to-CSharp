package org.overture.codegen.vdm2cs.unit.expressions.primary

import org.overture.codegen.ir.expressions.SVarExpIR
import org.overture.codegen.vdm2cs.translations.expressions.primary.Identifiers
import org.overture.codegen.vdm2cs.unit.expressions.ExpressionTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class IdentifierTranslations : ExpressionTranslationRuleSpek<SVarExpIR>(Identifiers)
{
    init
    {
        //region LOCAL VARIABLES

        nextLowerCasePlaceholder describesThat
            (identifier(lowerCasePlaceholder, boolType)) becomes
            lowerCasePlaceholder

        nextLowerCasePlaceholder describesThat
            (identifier(lowerCasePlaceholder, charType)) becomes
            lowerCasePlaceholder

        nextPlaceholder describesThat
            (identifier(placeholder, intType)) becomes
            lowerCasePlaceholder

        nextPlaceholder describesThat
            (identifier(placeholder, natType)) becomes
            lowerCasePlaceholder

        //endregion

        //region STATE FIELDS

        nextLowerCasePlaceholder describesThat
            (stateFieldIdentifier(lowerCasePlaceholder, boolType)) becomes
            "State.$placeholder"

        nextLowerCasePlaceholder describesThat
            (stateFieldIdentifier(lowerCasePlaceholder, charType)) becomes
            "State.$placeholder"

        nextPlaceholder describesThat
            (stateFieldIdentifier(placeholder, intType)) becomes
            "State.$placeholder"

        nextPlaceholder describesThat
            (stateFieldIdentifier(placeholder, natType)) becomes
            "State.$placeholder"

        //endregion

        //region VERBATIM IDENTIFIERS

        "abstract" describesThat
            (identifier("abstract", intType)) becomes
            "@abstract"

        "add" describesThat
            (identifier("add", intType)) becomes
            "@add"

        "alias" describesThat
            (identifier("alias", intType)) becomes
            "@alias"

        "as" describesThat
            (identifier("as", intType)) becomes
            "@as"

        "ascending" describesThat
            (identifier("ascending", intType)) becomes
            "@ascending"

        "async" describesThat
            (identifier("async", intType)) becomes
            "@async"

        "await" describesThat
            (identifier("await", intType)) becomes
            "@await"

        "base" describesThat
            (identifier("base", intType)) becomes
            "@base"

        "bool" describesThat
            (identifier("bool", intType)) becomes
            "@bool"

        "break" describesThat
            (identifier("break", intType)) becomes
            "@break"

        "by" describesThat
            (identifier("by", intType)) becomes
            "@by"

        "byte" describesThat
            (identifier("byte", intType)) becomes
            "@byte"

        "case" describesThat
            (identifier("case", intType)) becomes
            "@case"

        "catch" describesThat
            (identifier("catch", intType)) becomes
            "@catch"

        "char" describesThat
            (identifier("char", intType)) becomes
            "@char"

        "checked" describesThat
            (identifier("checked", intType)) becomes
            "@checked"

        "class" describesThat
            (identifier("class", intType)) becomes
            "@class"

        "const" describesThat
            (identifier("const", intType)) becomes
            "@const"

        "continue" describesThat
            (identifier("continue", intType)) becomes
            "@continue"

        "decimal" describesThat
            (identifier("decimal", intType)) becomes
            "@decimal"

        "default" describesThat
            (identifier("default", intType)) becomes
            "@default"

        "delegate" describesThat
            (identifier("delegate", intType)) becomes
            "@delegate"

        "descending" describesThat
            (identifier("descending", intType)) becomes
            "@descending"

        "do" describesThat
            (identifier("do", intType)) becomes
            "@do"

        "double" describesThat
            (identifier("double", intType)) becomes
            "@double"

        "dynamic" describesThat
            (identifier("dynamic", intType)) becomes
            "@dynamic"

        "else" describesThat
            (identifier("else", intType)) becomes
            "@else"

        "enum" describesThat
            (identifier("enum", intType)) becomes
            "@enum"

        "equals" describesThat
            (identifier("equals", intType)) becomes
            "@equals"

        "event" describesThat
            (identifier("event", intType)) becomes
            "@event"

        "explicit" describesThat
            (identifier("explicit", intType)) becomes
            "@explicit"

        "extern" describesThat
            (identifier("extern", intType)) becomes
            "@extern"

        "false" describesThat
            (identifier("false", intType)) becomes
            "@false"

        "finally" describesThat
            (identifier("finally", intType)) becomes
            "@finally"

        "fixed" describesThat
            (identifier("fixed", intType)) becomes
            "@fixed"

        "float" describesThat
            (identifier("float", intType)) becomes
            "@float"

        "for" describesThat
            (identifier("for", intType)) becomes
            "@for"

        "foreach" describesThat
            (identifier("foreach", intType)) becomes
            "@foreach"

        "from" describesThat
            (identifier("from", intType)) becomes
            "@from"

        "get" describesThat
            (identifier("get", intType)) becomes
            "@get"

        "global" describesThat
            (identifier("global", intType)) becomes
            "@global"

        "goto" describesThat
            (identifier("goto", intType)) becomes
            "@goto"

        "group" describesThat
            (identifier("group", intType)) becomes
            "@group"

        "if" describesThat
            (identifier("if", intType)) becomes
            "@if"

        "implicit" describesThat
            (identifier("implicit", intType)) becomes
            "@implicit"

        "in" describesThat
            (identifier("in", intType)) becomes
            "@in"

        "int" describesThat
            (identifier("int", intType)) becomes
            "@int"

        "interface" describesThat
            (identifier("interface", intType)) becomes
            "@interface"

        "internal" describesThat
            (identifier("internal", intType)) becomes
            "@internal"

        "into" describesThat
            (identifier("into", intType)) becomes
            "@into"

        "is" describesThat
            (identifier("is", intType)) becomes
            "@is"

        "join" describesThat
            (identifier("join", intType)) becomes
            "@join"

        "let" describesThat
            (identifier("let", intType)) becomes
            "@let"

        "lock" describesThat
            (identifier("lock", intType)) becomes
            "@lock"

        "long" describesThat
            (identifier("long", intType)) becomes
            "@long"

        "nameof" describesThat
            (identifier("nameof", intType)) becomes
            "@nameof"

        "namespace" describesThat
            (identifier("namespace", intType)) becomes
            "@namespace"

        "new" describesThat
            (identifier("new", intType)) becomes
            "@new"

        "null" describesThat
            (identifier("null", intType)) becomes
            "@null"

        "object" describesThat
            (identifier("object", intType)) becomes
            "@object"

        "on" describesThat
            (identifier("on", intType)) becomes
            "@on"

        "operator" describesThat
            (identifier("operator", intType)) becomes
            "@operator"

        "orderby" describesThat
            (identifier("orderby", intType)) becomes
            "@orderby"

        "out" describesThat
            (identifier("out", intType)) becomes
            "@out"

        "override" describesThat
            (identifier("override", intType)) becomes
            "@override"

        "params" describesThat
            (identifier("params", intType)) becomes
            "@params"

        "partial" describesThat
            (identifier("partial", intType)) becomes
            "@partial"

        "private" describesThat
            (identifier("private", intType)) becomes
            "@private"

        "protected" describesThat
            (identifier("protected", intType)) becomes
            "@protected"

        "public" describesThat
            (identifier("public", intType)) becomes
            "@public"

        "readonly" describesThat
            (identifier("readonly", intType)) becomes
            "@readonly"

        "ref" describesThat
            (identifier("ref", intType)) becomes
            "@ref"

        "remove" describesThat
            (identifier("remove", intType)) becomes
            "@remove"

        "return" describesThat
            (identifier("return", intType)) becomes
            "@return"

        "sbyte" describesThat
            (identifier("sbyte", intType)) becomes
            "@sbyte"

        "sealed" describesThat
            (identifier("sealed", intType)) becomes
            "@sealed"

        "select" describesThat
            (identifier("select", intType)) becomes
            "@select"

        "set" describesThat
            (identifier("set", intType)) becomes
            "@set"

        "short" describesThat
            (identifier("short", intType)) becomes
            "@short"

        "sizeof" describesThat
            (identifier("sizeof", intType)) becomes
            "@sizeof"

        "stackalloc" describesThat
            (identifier("stackalloc", intType)) becomes
            "@stackalloc"

        "static" describesThat
            (identifier("static", intType)) becomes
            "@static"

        "string" describesThat
            (identifier("string", intType)) becomes
            "@string"

        "struct" describesThat
            (identifier("struct", intType)) becomes
            "@struct"

        "switch" describesThat
            (identifier("switch", intType)) becomes
            "@switch"

        "this" describesThat
            (identifier("this", intType)) becomes
            "@this"

        "throw" describesThat
            (identifier("throw", intType)) becomes
            "@throw"

        "true" describesThat
            (identifier("true", intType)) becomes
            "@true"

        "try" describesThat
            (identifier("try", intType)) becomes
            "@try"

        "typeof" describesThat
            (identifier("typeof", intType)) becomes
            "@typeof"

        "uint" describesThat
            (identifier("uint", intType)) becomes
            "@uint"

        "ulong" describesThat
            (identifier("ulong", intType)) becomes
            "@ulong"

        "unchecked" describesThat
            (identifier("unchecked", intType)) becomes
            "@unchecked"

        "unsafe" describesThat
            (identifier("unsafe", intType)) becomes
            "@unsafe"

        "ushort" describesThat
            (identifier("ushort", intType)) becomes
            "@ushort"

        "using" describesThat
            (identifier("using", intType)) becomes
            "@using"

        "value" describesThat
            (identifier("value", intType)) becomes
            "@value"

        "var" describesThat
            (identifier("var", intType)) becomes
            "@var"

        "virtual" describesThat
            (identifier("virtual", intType)) becomes
            "@virtual"

        "void" describesThat
            (identifier("void", intType)) becomes
            "@void"

        "volatile" describesThat
            (identifier("volatile", intType)) becomes
            "@volatile"

        "when" describesThat
            (identifier("when", intType)) becomes
            "@when"

        "where" describesThat
            (identifier("where", intType)) becomes
            "@where"

        "while" describesThat
            (identifier("while", intType)) becomes
            "@while"

        "yield" describesThat
            (identifier("yield", intType)) becomes
            "@yield"

        //endregion
    }
}
