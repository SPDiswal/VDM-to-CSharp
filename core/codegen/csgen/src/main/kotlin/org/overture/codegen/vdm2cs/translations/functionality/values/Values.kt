package org.overture.codegen.vdm2cs.translations.functionality.values

import org.overture.codegen.ir.declarations.AFieldDeclIR
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsPropertyDeclaration
import org.overture.codegen.vdm2cs.parser.builders.publicStaticInitialisedReadonlyAutoProperty
import org.overture.codegen.vdm2cs.translations.functionality.DefinitionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object Values : DefinitionTranslationRule<AFieldDeclIR, CsPropertyDeclaration>
{
    override fun translate(irNode: AFieldDeclIR)
        = publicStaticInitialisedReadonlyAutoProperty(name = irNode.name.toUpperCamelCase(),
                                                      type = irNode.type.inline,
                                                      initialiser = irNode.initial.inline)
}
