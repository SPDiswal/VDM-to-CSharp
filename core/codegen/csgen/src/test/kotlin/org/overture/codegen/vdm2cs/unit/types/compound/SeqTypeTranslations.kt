package org.overture.codegen.vdm2cs.unit.types.compound

import org.overture.codegen.ir.types.SSeqTypeIR
import org.overture.codegen.vdm2cs.translations.types.compound.SeqTypes
import org.overture.codegen.vdm2cs.unit.types.TypeTranslationRuleSpek
import org.overture.codegen.vdm2cs.utilities.*

class SeqTypeTranslations : TypeTranslationRuleSpek<SSeqTypeIR>(SeqTypes)
{
    init
    {
        "seq of char" describesThat
            seqOf(charType) becomes
            "string"

        "seq of nat" describesThat
            seqOf(natType) becomes
            "List<int>"

        "seq of seq of char" describesThat
            seqOf(seqOf(charType)) becomes
            "List<string>"

        "seq of seq of nat" describesThat
            seqOf(seqOf(natType)) becomes
            "List<List<int>>"

        "seq1 of char" describesThat
            seq1Of(charType) becomes
            "string"

        "seq1 of nat" describesThat
            seq1Of(natType) becomes
            "List<int>"

        "seq1 of seq1 of char" describesThat
            seq1Of(seq1Of(charType)) becomes
            "List<string>"

        "seq1 of seq1 of nat" describesThat
            seq1Of(seq1Of(natType)) becomes
            "List<List<int>>"
    }
}
