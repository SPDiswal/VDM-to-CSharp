package org.overture.codegen.vdm2cs.utilities.ir

import org.overture.codegen.ir.STypeIR
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.expressions.SVarExpIR
import org.overture.codegen.ir.statements.ABlockStmIR

fun dataClassDeclaration(type: STypeIR, name: String, fields: List<AFieldDeclIR>): ADefaultClassDeclIR
{
    val dataClassDeclaration = sealedClassDeclaration(name, listOf("ICopyable<>", "IEquatable<>"))
    dataClassDeclaration.fields.addAll(fields.map { it.clone() })

    dataClassDeclaration.methods.add(iEquatableEqualsMethod(type, dataClassDeclaration))
    dataClassDeclaration.methods.add(objectEqualsMethod(type))

    return dataClassDeclaration
}

private fun iEquatableEqualsMethod(type: STypeIR, classDeclaration: SClassDeclIR): AMethodDeclIR
{
    val fields = significantFieldsOf(classDeclaration)
    val other = identifier("other", type)
    val equalsMethodBody = ABlockStmIR()

    if (fields.any())
    {
        // if (ReferenceEquals(null, other)) return false;
        equalsMethodBody.statements.add(ifThen(referenceEqualsNull(type, other), `return`(false)))

        // if (ReferenceEquals(this, other)) return true;
        equalsMethodBody.statements.add(ifThen(referenceEqualsThis(type, other), `return`(true)))

        // return firstField == other.firstField && secondField == other.secondField && ...;
        val fieldComparisons = fields.map { compareFields(it, other) }

        if (fields.size > 1)
        {
            val fieldComparisonConjunction = fieldComparisons.drop(2).fold(
                and(fieldComparisons[0], fieldComparisons[1]),
                { binaryAndsSoFar, fieldComparison -> and(binaryAndsSoFar, fieldComparison) })

            equalsMethodBody.statements.add(`return`(fieldComparisonConjunction))
        }
        else
        {
            // return firstField == other.firstField;
            equalsMethodBody.statements.add(`return`(compareFields(fields[0], other)))
        }
    }
    else
    {
        // return !ReferenceEquals(null, other);
        equalsMethodBody.statements.add(`return`(not(referenceEqualsNull(type, other))))
    }

    return methodDeclaration("Equals", listOf(other.asParameter()), boolType(), equalsMethodBody)
}

// TODO Filter out equality abstraction fields (seems to require an enhancement of the IR).
private fun significantFieldsOf(classDeclaration: SClassDeclIR)
    = classDeclaration.fields.map { identifier(it.name, it.type) }

private fun compareFields(field: SVarExpIR, otherIdentifier: SVarExpIR)
    = equalTo(field, invokeGetter(otherIdentifier, field))

private fun objectEqualsMethod(type: STypeIR): AMethodDeclIR
{
    val other = identifier("other", objectType())
    val equalsMethodBody = ABlockStmIR()

    // if (ReferenceEquals(null, other)) return false;
    equalsMethodBody.statements.add(ifThen(referenceEqualsNull(objectType(), other), `return`(false)))

    // if (ReferenceEquals(this, other)) return true;
    equalsMethodBody.statements.add(ifThen(referenceEqualsThis(objectType(), other), `return`(true)))

    // return (other is TType) && Equals((TType) obj);
    equalsMethodBody.statements.add(`return`(and(isOfType(other, type),
                                                 invoke("Equals", listOf(typeCast(other, type)), boolType()))))

    val objectEqualsMethodDeclaration = methodDeclaration("Equals",
                                                          listOf(other.asParameter()),
                                                          boolType(),
                                                          equalsMethodBody)

    objectEqualsMethodDeclaration.addModifier("override")

    return objectEqualsMethodDeclaration
}
