package org.overture.codegen.vdm2csharp;

import org.overture.codegen.ir.*;
import org.overture.ast.analysis.AnalysisException;
import org.overture.codegen.ir.declarations.*;
import org.overture.codegen.utils.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * The VDM-to-C# code generator transcompiles VDM-SL modules to C# classes.
 */
public final class CSharpCodeGenerator extends CodeGenBase
{
    /**
     * Transcompiles a collection of IR nodes representing VDM-SL modules to a collection of static C# classes.
     *
     * @param statuses the status-enhanced IR nodes to transcompile.
     *
     * @return The generated C# classes as well as information about the code generation process.
     *
     * @throws AnalysisException if something goes wrong during the code generation process.
     */
    @Override
    protected GeneratedData genVdmToTargetLang(final List<IRStatus<PIR>> statuses) throws AnalysisException
    {
        final GeneratedData generatedData = new GeneratedData();
        final List<GeneratedModule> generatedModules = new ArrayList<>();

        final List<ADefaultClassDeclIR> nodes = statuses.stream().map(n -> (ADefaultClassDeclIR) n.getIrNode())
                                                        .collect(toList());

        for (final ADefaultClassDeclIR node : nodes)
        {
            final String output = "namespace VdmToCSharp\n"
                                  + "{\n"
                                  + "    public class " + node.getName() + " { }\n"
                                  + "}";

            final GeneratedModule generatedModule = new GeneratedModule("Test", node, output, false);
            generatedModules.add(generatedModule);
        }

        generatedData.setClasses(generatedModules);

        return generatedData;
    }
}
