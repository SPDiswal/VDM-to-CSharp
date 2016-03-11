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

    // TODO Test records with no fields.
    // TODO Test records with a single field.

    init
    {
        given("a composite type AlphaRecord with fields of nat1 and bool; " +
              "and an implicit function with a return type of AlphaRecord")
        {
            val vdm = """
                types
                    AlphaRecord :: first: nat1
                                   second: bool;

                functions
                    AlphaFunc () r: AlphaRecord
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a nested sealed class called 'AlphaRecord' that implements IEquatable<AlphaRecord>")
                {
                    cs.shouldContain(
                        a("public sealed class AlphaRecord : IEquatable<AlphaRecord>") { anything() })
                }

                it("has a property of type int called 'first'")
                {
                    cs.shouldContain(
                        a("class AlphaRecord : IEquatable<AlphaRecord>")
                        {
                            a("public int first { get; set; }") +
                            anything()
                        })
                }

                it("has a property of type bool called 'second'")
                {
                    cs.shouldContain(
                        a("class AlphaRecord : IEquatable<AlphaRecord>")
                        {
                            anything() +
                            a("public bool second { get; set; }") +
                            anything()
                        })
                }

                it("implements the Equals method of IEquatable<AlphaRecord>")
                {
                    cs.shouldContain(
                        a("class AlphaRecord : IEquatable<AlphaRecord>")
                        {
                            anything() +
                            a("public bool Equals(AlphaRecord other)")
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return first == other.first && second == other.second;
                                """)
                            } +
                            anything()
                        })
                }

                it("overrides the Equals method of object")
                {
                    cs.shouldContain(
                        a("class AlphaRecord : IEquatable<AlphaRecord>")
                        {
                            anything() +
                            a("public override bool Equals(object other)")
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return other is AlphaRecord && Equals((AlphaRecord) other);
                                """)
                            } +
                            anything()
                        })
                }

                // TODO Overrides the GetHashCode method of object.

                // TODO Overrides the == and != operators.

                // TODO Overrides the ToString method of object.

                it("has a return type of DEFAULT.AlphaRecord")
                {
                    cs.shouldContain(a("DEFAULT.AlphaRecord AlphaFunc()"))
                }
            }
        }

        given("a composite type BravoRecord with fields of token, int and seq of char; " +
              "and an implicit function with a return type of BravoRecord")
        {
            val vdm = """
                types
                    BravoRecord :: id: token
                                   number: int
                                   text: seq of char;

                functions
                    BravoFunc () r: BravoRecord
                    post true;
                """

            on("transcompilation")
            {
                val cs = vdm.transcompile()

                it("has a nested sealed class called 'BravoRecord' that implements IEquatable<BravoRecord>")
                {
                    cs.shouldContain(
                        a("public sealed class BravoRecord : IEquatable<BravoRecord>") { anything() })
                }

                it("has a property of type object called 'id'")
                {
                    cs.shouldContain(
                        a("class BravoRecord : IEquatable<BravoRecord>")
                        {
                            a("public object id { get; set; }") +
                            anything()
                        })
                }

                it("has a property of type int called 'number'")
                {
                    cs.shouldContain(
                        a("class BravoRecord : IEquatable<BravoRecord>")
                        {
                            anything() +
                            a("public int number { get; set; }") +
                            anything()
                        })
                }

                it("has a property of type string called 'text'")
                {
                    cs.shouldContain(
                        a("class BravoRecord : IEquatable<BravoRecord>")
                        {
                            anything() +
                            a("public string text { get; set; }") +
                            anything()
                        })
                }

                it("implements the Equals method of IEquatable<BravoRecord>")
                {
                    cs.shouldContain(
                        a("class BravoRecord : IEquatable<BravoRecord>")
                        {
                            anything() +
                            a("public bool Equals(BravoRecord other)")
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return id == other.id && number == other.number && text == other.text;
                                """)
                            } +
                            anything()
                        })
                }

                it("overrides the Equals method of object")
                {
                    cs.shouldContain(
                        a("class BravoRecord : IEquatable<BravoRecord>")
                        {
                            anything() +
                            a("public override bool Equals(object other)")
                            {
                                a("""
                                if (ReferenceEquals(null, other)) return false;
                                if (ReferenceEquals(this, other)) return true;
                                return other is BravoRecord && Equals((BravoRecord) other);
                                """)
                            } +
                            anything()
                        })
                }

                // TODO Overrides the GetHashCode method of object.

                // TODO Overrides the == and != operators.

                // TODO Overrides the ToString method of object.

                it("has a return type of DEFAULT.BravoRecord")
                {
                    cs.shouldContain(a("DEFAULT.BravoRecord BravoFunc()"))
                }
            }
        }
    }
}
