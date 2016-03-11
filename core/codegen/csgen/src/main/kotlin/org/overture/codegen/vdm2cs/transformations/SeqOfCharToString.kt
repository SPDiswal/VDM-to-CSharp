package org.overture.codegen.vdm2cs.transformations

import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.types.*
import org.overture.codegen.trans.assistants.TransAssistantIR

/**
 * This transformation replaces occurrences of 'seq of char' with 'string'.
 */
final class SeqOfCharToString(val transform: TransAssistantIR) : DepthFirstAnalysisAdaptor()
{
    override fun caseASeqSeqTypeIR(seqType: ASeqSeqTypeIR)
    {
        if (seqType.seqOf is ACharBasicTypeIR)
        {
            val stringType = AStringTypeIR()
            transform.replaceNodeWith(seqType, stringType)
        }
    }
}
