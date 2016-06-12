package org.overture.codegen.vdm2cs.translations

import org.overture.codegen.ir.PIR
import org.overture.codegen.vdm2cs.parser.ast.CsNode

interface TranslationRule<in TIrNode : PIR, out TCsNode : CsNode>
{
    fun translate(irNode: TIrNode): TCsNode
}
