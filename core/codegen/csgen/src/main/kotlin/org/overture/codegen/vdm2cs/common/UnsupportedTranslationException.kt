package org.overture.codegen.vdm2cs.common

import org.overture.codegen.ir.PIR

class UnsupportedTranslationException : RuntimeException
{
    constructor(irNode: PIR) : super(
        "There is no translation for '${irNode.javaClass.simpleName}' ($irNode) available yet.")

    constructor(message: String) : super(message)
}
