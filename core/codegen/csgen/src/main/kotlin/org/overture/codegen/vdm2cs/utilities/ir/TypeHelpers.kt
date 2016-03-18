package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.*
import org.overture.codegen.ir.name.ATypeNameIR
import org.overture.codegen.ir.types.*

fun boolType() = ABoolBasicTypeIR()

fun objectType() = AObjectTypeIR()

fun voidType() = AVoidTypeIR()

fun classType(name: String): AClassTypeIR
{
    val classType = AClassTypeIR()
    classType.name = name

    return classType
}

fun methodType(argumentTypes: List<STypeIR>, returnType: STypeIR): AMethodTypeIR
{
    val methodType = AMethodTypeIR()
    methodType.params.addAll(argumentTypes.map { it.clone() })
    methodType.result = returnType.clone()

    return methodType
}

fun recordType(name: String, qualifier: String? = null): ARecordTypeIR
{
    val typeName = ATypeNameIR()
    typeName.definingClass = qualifier
    typeName.name = name

    val recordType = ARecordTypeIR()
    recordType.name = typeName

    return recordType
}
