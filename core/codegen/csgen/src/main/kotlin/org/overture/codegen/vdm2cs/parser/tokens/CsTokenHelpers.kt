package org.overture.codegen.vdm2cs.parser.tokens

import me.sargunvohra.lib.cakeparse.api.*
import me.sargunvohra.lib.cakeparse.lexer.TokenInstance
import me.sargunvohra.lib.cakeparse.parser.Parser

val CsTokenDefinition.value: Parser<String>
    get() = this.token map { it.raw }

infix fun CsTokenDefinition.and(other: CsTokenDefinition) = this.token and other.token

infix fun <A> CsTokenDefinition.and(other: Parser<A>) = this.token and other

infix fun <A> Parser<A>.and(other: CsTokenDefinition) = this and other.token

infix fun CsTokenDefinition.before(other: CsTokenDefinition) = this.token before other.token

infix fun <A> CsTokenDefinition.before(other: Parser<A>) = this.token before other

infix fun <A> Parser<A>.before(other: CsTokenDefinition) = this before other.token

infix fun CsTokenDefinition.or(other: CsTokenDefinition) = this.token or other.token

infix fun <A> CsTokenDefinition.or(other: Parser<A>) = this.token or other

infix fun <A> Parser<A>.or(other: CsTokenDefinition) = this or other.token

infix fun CsTokenDefinition.then(other: CsTokenDefinition) = this.token then other.token

infix fun <A> CsTokenDefinition.then(other: Parser<A>) = this.token then other

infix fun <A> Parser<A>.then(other: CsTokenDefinition) = this then other.token

infix fun <A> CsTokenDefinition.map(transform: (TokenInstance) -> A) = this.token.map { transform(it) }

fun optional(target: CsTokenDefinition) = optional(target.token)

fun <A> zeroOrMoreSeparatedBy(target: Parser<A>, separator: CsTokenDefinition)
    = org.overture.codegen.vdm2cs.parser.zeroOrMoreSeparatedBy(target, separator.token)

fun <A> oneOrMoreSeparatedBy(target: Parser<A>, separator: CsTokenDefinition): Parser<List<A>>
    = org.overture.codegen.vdm2cs.parser.oneOrMoreSeparatedBy(target, separator.token)

//fun modifiers(vararg targets: Pair<CsTokenDefinition, CsModifier>) = oneOf(*targets.map {
//    val (csToken, modifier) = it
//    csToken map { listOf(modifier) }
//}.toTypedArray())
