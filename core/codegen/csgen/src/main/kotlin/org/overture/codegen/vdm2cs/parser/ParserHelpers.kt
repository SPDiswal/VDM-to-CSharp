package org.overture.codegen.vdm2cs.parser

import me.sargunvohra.lib.cakeparse.api.*
import me.sargunvohra.lib.cakeparse.lexer.TokenInstance
import me.sargunvohra.lib.cakeparse.parser.Parser
import org.overture.codegen.vdm2cs.parser.tokens.CsTokenDefinition
import org.overture.codegen.vdm2cs.utilities.*

fun <A, B> zeroOrMoreSeparatedBy(target: Parser<A>, separator: Parser<B>)
    = optional(target and zeroOrMore(separator then target)) map { if (it != null) combine(it) else emptyList() }

fun <A, B> oneOrMoreSeparatedBy(target: Parser<A>, separator: Parser<B>)
    = target and zeroOrMore(separator then target) map { combine(it) }

fun <A> combine(it: Pair<A, List<A>>): List<A>
{
    val (head, tail) = it
    return listOf(head) + tail
}

fun oneOf(vararg tokens: CsTokenDefinition) = tokens.drop(1).map { it.token }.fold(tokens.first().token)
{
    result: Parser<TokenInstance>, current ->
    result or current
}

fun oneOf(tokens: List<CsTokenDefinition>) = oneOf(*tokens.toTypedArray())

infix fun <A, B, C, D> Parser<Pair<Pair<A, B>, C>>.mapTriple(transform: (Triple<A, B, C>) -> D)
    = map { transform(it.toTriple()) }

infix fun <A, B, C, D, E> Parser<Pair<Pair<Pair<A, B>, C>, D>>.mapQuadruple(transform: (Quadruple<A, B, C, D>) -> E)
    = map { transform(it.toQuadruple()) }

infix fun <A, B, C, D, E, F> Parser<Pair<Pair<Pair<Pair<A, B>, C>, D>, E>>.mapQuintuple(
    transform: (Quintuple<A, B, C, D, E>) -> F) = map { transform(it.toQuintuple()) }

infix fun <A, B, C, D, E, F, G> Parser<Pair<Pair<Pair<Pair<Pair<A, B>, C>, D>, E>, F>>.mapSextuple(
    transform: (Sextuple<A, B, C, D, E, F>) -> G) = map { transform(it.toSextuple()) }

infix fun <A, B, C, D, E, F, G, H> Parser<Pair<Pair<Pair<Pair<Pair<Pair<A, B>, C>, D>, E>, F>, G>>.mapSeptuple(
    transform: (Septuple<A, B, C, D, E, F, G>) -> H) = map { transform(it.toSeptuple()) }
