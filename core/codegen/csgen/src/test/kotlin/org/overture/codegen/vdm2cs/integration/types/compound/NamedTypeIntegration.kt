package org.overture.codegen.vdm2cs.integration.types.compound

import org.jetbrains.spek.api.Spek

final class NamedTypeIntegration : Spek()
//{
//    // TODO Test type names with invariants to see that invariants are preserved upon inlining the type alias
//    //      perhaps by adding pre- and postconditions to function/operation parameters in the transcompiled code.
//
//    // TODO Test aliases for composite types (records).
//
//    // TODO Generate invariants for certain VDM types such as nat, nat1, seq1 and non-optional types.
//
//    private val basicTypes = listOf("bool" to "bool",
//                                    "char" to "char",
//                                    "int" to "int",
//                                    "nat" to "int",
//                                    "nat1" to "int",
//                                    "rat" to "double",
//                                    "real" to "double",
//                                    "token" to "Token")
//
//    private val seqTypes = listOf("seq of char" to "string",
//                                  "seq of int" to "List<int>")
//
//    private val setTypes = listOf("set of char" to "HashSet<char>",
//                                  "set of int" to "HashSet<int>")
//
//    private val singleNamedType = basicTypes
//        .plus(seqTypes)
//        .plus(setTypes)
//        .toNamedTypeTheory()
//
//    init
//    {
//        givenData(singleNamedType)
//        {
//            val (vdmType, csType, functionName) = it
//            val vdmNamedType = it.vdmNamedType
//
//            val vdm = """
//                types
//                    $vdmNamedType = $vdmType;
//
//                functions
//                    $functionName (x: $vdmNamedType) r: $vdmNamedType
//                    post true;
//                """
//
//            on("transcompilation")
//            {
//                val cs = vdm.transcompile()
//
//                it("declares the nested class '$vdmNamedType' that implements 'ICopyable<$vdmNamedType>' and 'IEquatable<$vdmNamedType>'")
//                {
//                    cs.shouldContain(
//                        aClassDeclaration(vdmNamedType,
//                                          listOf("ICopyable<>", "IEquatable<>")))
//                }
//
//                it("declares the property 'Value' of type '$csType'")
//                {
//                    cs.shouldContain(
//                        inTopOfClass(vdmNamedType) { aPropertyDeclaration(csType, "Value") })
//                }
//
//                it("implements the 'Equals' method of 'IEquatable<$vdmNamedType>'")
//                {
//                    cs.shouldContain(
//                        inMidstOfClass(vdmNamedType)
//                        {
//                            aMethodDeclaration("bool", "Equals", listOf(vdmNamedType to "other"))
//                            {
//                                a("""
//                                if (ReferenceEquals(null, other)) return false;
//                                if (ReferenceEquals(this, other)) return true;
//                                return Value == other.Value;
//                                """)
//                            }
//                        })
//                }
//
//                it("overrides the 'Equals' method of 'object'")
//                {
//                    cs.shouldContain(
//                        inMidstOfClass(vdmNamedType)
//                        {
//                            anOverridingMethodDeclaration("bool", "Equals", listOf("object" to "other"))
//                            {
//                                a("""
//                                if (ReferenceEquals(null, other)) return false;
//                                if (ReferenceEquals(this, other)) return true;
//                                return other is $vdmNamedType && Equals(($vdmNamedType) other);
//                                """)
//                            }
//                        })
//                }
//
//                // TODO Overrides the GetHashCode method of object.
//
//                // TODO Overrides the == and != operators.
//
//                // TODO Overrides the ToString method of object.
//
//                // TODO Implements the Copy method of ICopyable.
//
//                it("has a return type of '$vdmNamedType'")
//                {
//                    cs.shouldContain(aPureStaticMethodDeclaration(vdmNamedType,
//                                                                  functionName,
//                                                                  listOf(vdmNamedType to "x")))
//                }
//            }
//        }
//    }
//
//    //        //        given("a type name 'SetOfNatAlias' for set of nat " +
//    //        //              "and an implicit function with a return type of SetOfNatAlias")
//    //        //        {
//    //        //            val vdm = """
//    //        //                types
//    //        //                    SetOfNatAlias = set of nat;
//    //        //
//    //        //                functions
//    //        //                    Bravo () s: SetOfNatAlias
//    //        //                    post true;
//    //        //                """
//    //        //
//    //        //            on("transcompilation")
//    //        //            {
//    //        //                val cs = vdm.transcompile()
//    //        //
//    //        //                it("has a return type of HashSet<int>") { cs.shouldContain(a("HashSet<int> Bravo()")) }
//    //        //            }
//    //        //        }
//    //
//    //        //        given("a type name 'BoolAlias' for bool " +
//    //        //              "and another type name 'AnotherBoolAlias' for BoolAlias " +
//    //        //              "and an implicit function with a return type of AnotherBoolAlias")
//    //        //        {
//    //        //            val vdm = """
//    //        //                types
//    //        //                    BoolAlias = bool;
//    //        //                    AnotherBoolAlias = BoolAlias;
//    //        //
//    //        //                functions
//    //        //                    Charlie () b: AnotherBoolAlias
//    //        //                    post true;
//    //        //                """
//    //        //
//    //        //            on("transcompilation")
//    //        //            {
//    //        //                val cs = vdm.transcompile()
//    //        //
//    //        //                it("has a return type of bool") { cs.shouldContain(a("bool Charlie()")) }
//    //        //            }
//    //        //        }
//    //
//    //        //        given("a type name 'NatAlias' for nat " +
//    //        //              "and another type name 'SetOfNatAlias' for set of NatAlias " +
//    //        //              "and another type name 'AnotherSetOfNatAlias' for set of SetOfNatAlias " +
//    //        //              "and an implicit function with a return type of AnotherSetOfNatAlias")
//    //        //        {
//    //        //            val vdm = """
//    //        //                types
//    //        //                    NatAlias = nat;
//    //        //                    SetOfNatAlias = set of NatAlias;
//    //        //                    AnotherSetOfNatAlias = SetOfNatAlias;
//    //        //
//    //        //                functions
//    //        //                    Delta () s: AnotherSetOfNatAlias
//    //        //                    post true;
//    //        //                """
//    //        //
//    //        //            on("transcompilation")
//    //        //            {
//    //        //                val cs = vdm.transcompile()
//    //        //
//    //        //                it("has a return type of HashSet<int>") { cs.shouldContain(a("HashSet<int> Delta()")) }
//    //        //            }
//    //        //        }
//    //    }
//
//    private data class NamedTypeTheoryData(val vdmType: String, val csType: String, val functionName: String)
//    {
//        val vdmNamedType = "${vdmType.split(" ").map { it.capitalize() }.joinToString("")}Alias"
//
//        override fun toString() = "the implicit function '$functionName' with a parameter of '$vdmNamedType' and a return type of '$vdmNamedType'"
//    }
//
//    private fun List<Pair<String, String>>.toNamedTypeTheory() = this.withIndex().map {
//        NamedTypeTheoryData(it.value.first, it.value.second, placeholders[it.index % placeholders.size])
//    }
//}
