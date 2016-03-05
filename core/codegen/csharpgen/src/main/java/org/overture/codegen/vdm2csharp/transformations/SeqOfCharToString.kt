package org.overture.codegen.vdm2csharp.transformations

import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.types.*
import org.overture.codegen.trans.assistants.TransAssistantIR

/**
 * This transformation replaces an occurrence of 'seq of char' with 'string'.
 */
final class SeqOfCharToString(val transformer: TransAssistantIR) : DepthFirstAnalysisAdaptor()
{
    override fun caseASeqSeqTypeIR(seqType: ASeqSeqTypeIR)
    {
        if (seqType.seqOf is ACharBasicTypeIR)
        {
            val stringType = AStringTypeIR()
            transformer.replaceNodeWith(seqType, stringType)
        }
    }
}
