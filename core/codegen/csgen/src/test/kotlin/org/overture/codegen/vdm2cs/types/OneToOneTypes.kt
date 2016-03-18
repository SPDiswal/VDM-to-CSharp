package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.*
import org.overture.codegen.vdm2cs.utilities.*

final class OneToOneTypes : Spek()
{
    private val basicTypes = listOf("bool" to "bool",
                                    "char" to "char",
                                    "int" to "int",
                                    "nat" to "int",
                                    "nat1" to "int",
                                    "rat" to "double",
                                    "real" to "double",
                                    "token" to "Token")

    private val mapTypes = listOf("map char to bool" to "IDictionary<char, bool>",
                                  "map int to int" to "IDictionary<int, int>",
                                  "map real to map int to int"
                                      to "IDictionary<double, IDictionary<int, int>>",
                                  "inmap char to bool" to "IDictionary<char, bool>",
                                  "inmap int to int" to "IDictionary<int, int>",
                                  "inmap real to map int to int"
                                      to "IDictionary<double, IDictionary<int, int>>")

    private val optionalTypes = listOf("[bool]" to "bool?",
                                       "[char]" to "char?",
                                       "[int]" to "int?",
                                       "[nat]" to "int?",
                                       "[nat1]" to "int?",
                                       "[rat]" to "double?",
                                       "[real]" to "double?",
                                       "[token]" to "Token",
                                       "[seq of char]" to "string",
                                       "[seq of [int]]" to "IList<int?>")

    // TODO Support tuples with more than seven items (the C# Tuple type only supports up to seven items by default).

    private val productTypes = listOf("nat * nat" to "Tuple<int, int>",
                                      "bool * char * int" to "Tuple<bool, char, int>",
                                      "seq of char * set of int * nat1 * rat * real"
                                          to "Tuple<string, ISet<int>, int, double, double>",
                                      "(nat * nat) * (real * real)"
                                          to "Tuple<Tuple<int, int>, Tuple<double, double>>")

    private val seqTypes = listOf("seq of char" to "string",
                                  "seq of int" to "IList<int>",
                                  "seq of seq of real" to "IList<IList<double>>",
                                  "seq1 of char" to "string",
                                  "seq1 of int" to "IList<int>",
                                  "seq1 of seq of real" to "IList<IList<double>>")

    private val setTypes = listOf("set of char" to "ISet<char>",
                                  "set of int" to "ISet<int>",
                                  "set of set of real" to "ISet<ISet<double>>")

    private val data = basicTypes
        .plus(mapTypes)
        .plus(optionalTypes)
        .plus(productTypes)
        .plus(seqTypes)
        .plus(setTypes)
        .toOneToOneTypeTheory()

    init
    {
        givenData(data)
        {
            val (vdmType, csType, functionName) = it

            val vdm = """
                functions
                    $functionName () r: $vdmType
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a return type of '$csType'")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration(csType, functionName))
                }
            }
        }
    }

    private data class OneToOneTypeTheoryData(val vdmType: String, val csType: String, val functionName: String)
    {
        override fun toString() = "the implicit function '$functionName' with a return type of '$vdmType'"
    }

    private fun List<Pair<String, String>>.toOneToOneTypeTheory() = this.withIndex().map {
        OneToOneTypeTheoryData(it.value.first, it.value.second, placeholders[it.index % placeholders.size])
    }
}
