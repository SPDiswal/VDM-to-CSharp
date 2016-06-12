package org.overture.codegen.vdm2cs.translations.functionality.states

import org.overture.codegen.ir.declarations.AStateDeclIR
import org.overture.codegen.vdm2cs.common.DefaultNames
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsPropertyDeclaration
import org.overture.codegen.vdm2cs.parser.builders.privateStaticAutoProperty
import org.overture.codegen.vdm2cs.translations.functionality.DefinitionTranslationRule
import org.overture.codegen.vdm2cs.utilities.*

object States : DefinitionTranslationRule<AStateDeclIR, CsPropertyDeclaration>
{
    override fun translate(irNode: AStateDeclIR) =
        privateStaticAutoProperty(name = DefaultNames.STATE_NAME,
                                  type = irNode.name.toUpperCamelCase(),
                                  initialiser = irNode.initExp?.inline)
}
