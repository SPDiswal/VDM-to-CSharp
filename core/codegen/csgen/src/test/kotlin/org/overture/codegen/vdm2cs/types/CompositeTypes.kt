package org.overture.codegen.vdm2cs.types

import org.jetbrains.spek.api.Spek
import org.overture.codegen.vdm2cs.utilities.*

final class CompositeTypes : Spek()
{
    // TODO Handle composite types defined by 'compose ID of ... end' used directly without a type alias,
    //      e.g. a map type definition: map int to compose ID of ... end is transcompiled to IDictionary<int, ID>.
    // TODO Handle anonymous fields.
    // TODO Handle equality abstraction fields.
    // TODO Handle comparison of floating point numbers (of type double) (in another specification than this).

    init
    {
        given("a composite type AlphaRecord with no fields " +
              "and an implicit function with a return type of AlphaRecord")
        {
            val vdm = """
                types
                    AlphaRecord :: ;

                functions
                    AlphaFunc () r: AlphaRecord
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("declares the nested class 'AlphaRecord' " +
                   "that implements ICopyable<AlphaRecord> and IEquatable<AlphaRecord>")
                {
                    cs.shouldContain(
                        aClassDeclaration("AlphaRecord",
                                          listOf("ICopyable<>", "IEquatable<>")))
                }

                it("implements the Equals method of IEquatable<AlphaRecord>")
                {
                    cs.shouldContain(
                        inMidstOfClass("AlphaRecord")
                        {
                            aMethodDeclaration("bool", "Equals", listOf(Pair("AlphaRecord", "other")))
                            {
                                a("return !ReferenceEquals(null, other);")
                            }
                        })
                }

                it("overrides the Equals method of object")
                {
                    cs.shouldContain(
                        inMidstOfClass("AlphaRecord")
                        {
                            anOverridingMethodDeclaration("bool", "Equals", listOf(Pair("object", "other")))
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return other is AlphaRecord && Equals((AlphaRecord) other);
                                """)
                            }
                        })
                }

                // TODO Overrides the GetHashCode method of object.

                // TODO Overrides the == and != operators.

                // TODO Overrides the ToString method of object.

                // TODO Implements the Copy method of ICopyable.

                it("has a return type of AlphaRecord")
                {
                    cs.shouldContain(aPureStaticMethodDeclaration("AlphaRecord", "AlphaFunc"))
                }
            }
        }

        given("a composite type BravoRecord with a single field of bool")
        {
            val vdm = """
                types
                    BravoRecord :: b: bool;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("declares the nested class 'BravoRecord' " +
                   "that implements ICopyable<BravoRecord> and IEquatable<BravoRecord>")
                {
                    cs.shouldContain(
                        aClassDeclaration("BravoRecord",
                                          listOf("ICopyable<>", "IEquatable<>")))
                }

                it("declares the property 'b' of type bool")
                {
                    cs.shouldContain(
                        inTopOfClass("BravoRecord") { aPropertyDeclaration("bool", "b") })
                }

                it("implements the Equals method of IEquatable<BravoRecord>")
                {
                    cs.shouldContain(
                        inMidstOfClass("BravoRecord")
                        {
                            aMethodDeclaration("bool", "Equals", listOf(Pair("BravoRecord", "other")))
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return b == other.b;
                                """)
                            }
                        })
                }

                it("overrides the Equals method of object")
                {
                    cs.shouldContain(
                        inMidstOfClass("BravoRecord")
                        {
                            anOverridingMethodDeclaration("bool", "Equals", listOf(Pair("object", "other")))
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return other is BravoRecord && Equals((BravoRecord) other);
                                """)
                            }
                        })
                }

                // TODO Overrides the GetHashCode method of object.

                // TODO Overrides the == and != operators.

                // TODO Overrides the ToString method of object.

                // TODO Implements the Copy method of ICopyable.
            }
        }

        given("a composite type CharlieRecord with fields of nat1 and bool")
        {
            val vdm = """
                types
                    CharlieRecord :: first: nat1
                                     second: bool;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("declares the property 'first' of type int")
                {
                    cs.shouldContain(
                        inTopOfClass("CharlieRecord") { aPropertyDeclaration("int", "first") })
                }

                it("declares the property 'second' of type bool")
                {
                    cs.shouldContain(
                        inMidstOfClass("CharlieRecord") { aPropertyDeclaration("bool", "second") })
                }

                it("implements the Equals method of IEquatable<CharlieRecord>")
                {
                    cs.shouldContain(
                        inMidstOfClass("CharlieRecord")
                        {
                            aMethodDeclaration("bool", "Equals", listOf(Pair("CharlieRecord", "other")))
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return first == other.first && second == other.second;
                                """)
                            }
                        })
                }

                // TODO Overrides the GetHashCode method of object.

                // TODO Overrides the ToString method of object.

                // TODO Implements the Copy method of ICopyable.
            }
        }

        given("a composite type DeltaRecord with fields of token, int and seq of char")
        {
            val vdm = """
                        types
                            DeltaRecord :: id: token
                                           number: int
                                           text: seq of char;
                        """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("declares the property 'id' of type Token")
                {
                    cs.shouldContain(
                        inTopOfClass("DeltaRecord") { aPropertyDeclaration("Token", "id") })
                }

                it("declares the property 'number' of type int")
                {
                    cs.shouldContain(
                        inMidstOfClass("DeltaRecord") { aPropertyDeclaration("int", "number") })
                }

                it("declares the property 'text' of type string")
                {
                    cs.shouldContain(
                        inMidstOfClass("DeltaRecord") { aPropertyDeclaration("string", "text") })
                }

                it("implements the Equals method of IEquatable<DeltaRecord>")
                {
                    cs.shouldContain(
                        inMidstOfClass("DeltaRecord")
                        {
                            aMethodDeclaration("bool", "Equals", listOf(Pair("DeltaRecord", "other")))
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return id == other.id && number == other.number && text == other.text;
                                """)
                            }
                        })
                }

                // TODO Overrides the GetHashCode method of object.

                // TODO Overrides the ToString method of object.

                // TODO Implements the Copy method of ICopyable.
            }
        }
    }
}
