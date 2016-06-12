package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.parser.ast.CsNode

interface CsBuilder<TNode : CsNode>
{
    val ast: TNode
}
