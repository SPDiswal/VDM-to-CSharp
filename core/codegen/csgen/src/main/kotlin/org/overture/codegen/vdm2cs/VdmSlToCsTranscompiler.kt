package org.overture.codegen.vdm2cs

import org.overture.codegen.ir.declarations.AModuleDeclIR
import org.overture.codegen.vdm2cs.common.DefaultNames
import org.overture.codegen.vdm2cs.parser.builders.*
import org.overture.codegen.vdm2cs.translations.functionality.specifications.Specifications
import org.overture.codegen.vdm2cs.utilities.toUpperCamelCase

object VdmSlToCsTranscompiler
{
    fun transcompileSpecification(irNode: AModuleDeclIR)
        = csDocument(definedSymbols = listOf("CONTRACTS_FULL"),
                     importedTypes = listOf("System",
                                            "System.Collections.Generic",
                                            "System.Diagnostics.Contracts",
                                            "System.Linq",
                                            "VdmToCs.Quotes"),
                     staticallyImportedTypes = listOf("VdmToCs.VdmToCsUtilities"),
                     namespaceName = DefaultNames.VDM_CS_NAMESPACE)
    {
        +Specifications.translate(irNode)
    }

    fun transcompileQuotes(quotes: List<String>)
        = csDocument(definedSymbols = emptyList(),
                     importedTypes = emptyList(),
                     staticallyImportedTypes = emptyList(),
                     namespaceName = DefaultNames.QUOTES_NAMESPACE)
    {
        +publicEnum("Quote") { +quotes.map { enumConstant(name = it.toUpperCamelCase()) } }
    }
}
