package org.overture.codegen.vdm2csharp

import org.overture.codegen.ir.CodeGenBase
import org.overture.typechecker.util.TypeCheckerUtil

import static java.util.stream.Collectors.toList

class Utilities
{
    public static List<String> transcompile(String vdm)
    {
        def generator = new CSharpCodeGenerator()
        def parsedVdm = TypeCheckerUtil.typeCheckPp(vdm).result
        def validatedVdm = CodeGenBase.getNodes(parsedVdm)
        def cSharpResult = generator.generate(validatedVdm)
        cSharpResult.classes.stream().map({ it.content }).collect(toList());
    }

    public static String wrapInDefaultNamespace(String cSharp)
    {
        """namespace VdmToCSharp
          |{
          |    $cSharp
          |}""".stripMargin()
    }
}
