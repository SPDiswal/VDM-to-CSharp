package org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.fields

import org.overture.codegen.vdm2cs.framework.utilities.builders.vdm.VdmBuilder

interface VdmField : VdmBuilder
{
    val name: String?

    val type: String
}
