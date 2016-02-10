package org.overture.codegen.vdm2csharp

import org.overture.ast.lex.Dialect
import org.overture.config.Release
import org.overture.config.Settings
import spock.lang.Specification

import static java.util.stream.Collectors.toList

class ClassesSpecification extends Specification
{
    def setup()
    {
        Settings.dialect = Dialect.VDM_PP
        Settings.release = Release.VDM_10
    }

    def "Transcompiling a single empty class"()
    {
        expect: "An empty VDM++ class to become an empty C# class of the same name"
        def transcompiledCSharp = Utilities.transcompile(vdm.stripMargin())
        def expectedCSharp = cSharp.stream().map({ Utilities.wrapInDefaultNamespace(it) }).collect(toList())

        transcompiledCSharp[0] == expectedCSharp[0]

        where:
        vdm                                     || cSharp
        """ class SomeEmptyClass
            end SomeEmptyClass"""    || ["""public class SomeEmptyClass { }"""]

        """ class AnotherEmptyClass
            end AnotherEmptyClass""" || ["""public class AnotherEmptyClass { }"""]
    }

    def "Transcompiling three empty classes"()
    {
        expect: "Three empty VDM++ classes to become three empty C# classes of the same names"
        def transcompiledCSharp = Utilities.transcompile(vdm.stripMargin())
        def expectedCSharp = cSharp.stream().map({ Utilities.wrapInDefaultNamespace(it) }).collect(toList())

        transcompiledCSharp[0] == expectedCSharp[0]
        transcompiledCSharp[1] == expectedCSharp[1]

        where:
        vdm                                           || cSharp
        """ class SomeEmptyFirstClass
            end SomeEmptyFirstClass

            class SomeEmptySecondClass
            end SomeEmptySecondClass

            class SomeEmptyThirdClass
            end SomeEmptyThirdClass """    || ["""public class SomeEmptyFirstClass { }""",

                                               """public class SomeEmptySecondClass { }""",

                                               """public class SomeEmptyThirdClass { }"""]

        """ class AnotherEmptyFirstClass
            end AnotherEmptyFirstClass

            class AnotherEmptySecondClass
            end AnotherEmptySecondClass

            class AnotherEmptyThirdClass
            end AnotherEmptyThirdClass """ || ["""public class AnotherEmptyFirstClass { }""",

                                               """public class AnotherEmptySecondClass { }""",

                                               """public class AnotherEmptyThirdClass { }"""]
    }
}
