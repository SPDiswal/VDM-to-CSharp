package org.overture.codegen.vdm2cs.integration.declarations

import org.overture.codegen.vdm2cs.framework.TranscompilerIntegrationSpek

final class FlatSpecifications : TranscompilerIntegrationSpek()
{
    init
    {
        vdm { } becomes cs { }
    }
}
