package org.overture.codegen.vdm2cs.utilities

import java.io.Serializable

data class Quadruple<out A, out B, out C, out D>(val first: A,
                                                 val second: B,
                                                 val third: C,
                                                 val fourth: D) : Serializable
{
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

data class Quintuple<out A, out B, out C, out D, out E>(val first: A,
                                                        val second: B,
                                                        val third: C,
                                                        val fourth: D,
                                                        val fifth: E) : Serializable
{
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

data class Sextuple<out A, out B, out C, out D, out E, out F>(val first: A,
                                                              val second: B,
                                                              val third: C,
                                                              val fourth: D,
                                                              val fifth: E,
                                                              val sixth: F) : Serializable
{
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

data class Septuple<out A, out B, out C, out D, out E, out F, out G>(val first: A,
                                                                     val second: B,
                                                                     val third: C,
                                                                     val fourth: D,
                                                                     val fifth: E,
                                                                     val sixth: F,
                                                                     val seventh: G) : Serializable
{
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

fun <A, B, C> Pair<Pair<A, B>, C>.toTriple()
    = Triple(this.first.first,
             this.first.second,
             this.second)

fun <A, B, C, D> Pair<Pair<Pair<A, B>, C>, D>.toQuadruple()
    = Quadruple(this.first.first.first,
                this.first.first.second,
                this.first.second,
                this.second)

fun <A, B, C, D, E> Pair<Pair<Pair<Pair<A, B>, C>, D>, E>.toQuintuple()
    = Quintuple(this.first.first.first.first,
                this.first.first.first.second,
                this.first.first.second,
                this.first.second,
                this.second)

fun <A, B, C, D, E, F> Pair<Pair<Pair<Pair<Pair<A, B>, C>, D>, E>, F>.toSextuple()
    = Sextuple(this.first.first.first.first.first,
               this.first.first.first.first.second,
               this.first.first.first.second,
               this.first.first.second,
               this.first.second,
               this.second)

fun <A, B, C, D, E, F, G> Pair<Pair<Pair<Pair<Pair<Pair<A, B>, C>, D>, E>, F>, G>.toSeptuple()
    = Septuple(this.first.first.first.first.first.first,
               this.first.first.first.first.first.second,
               this.first.first.first.first.second,
               this.first.first.first.second,
               this.first.first.second,
               this.first.second,
               this.second)
