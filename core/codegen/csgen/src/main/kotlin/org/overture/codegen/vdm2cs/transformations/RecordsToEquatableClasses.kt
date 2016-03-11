package org.overture.codegen.vdm2cs.transformations

import org.overture.codegen.ir.analysis.DepthFirstAnalysisAdaptor
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.expressions.SVarExpIR
import org.overture.codegen.ir.statements.ABlockStmIR
import org.overture.codegen.vdm2cs.utilities.ir.*

/**
 * This transformation implements the IEquatable.Equals, object.Equals, object.GetHashCode and object.ToString methods
 * for a record declaration.
 */
final class RecordsToEquatableClasses() : DepthFirstAnalysisAdaptor()
{
    override fun caseARecordDeclIR(recordDeclaration: ARecordDeclIR)
    {
        recordDeclaration.methods.add(iEquatableEqualsMethod(recordDeclaration))
        recordDeclaration.methods.add(objectEqualsMethod(recordDeclaration))
    }

    private fun iEquatableEqualsMethod(recordDeclaration: ARecordDeclIR): AMethodDeclIR
    {
        val recordType = recordType(recordDeclaration.name)
        val fields = significantFieldsOf(recordDeclaration)
        val otherIdentifier = identifier("other", recordType)

        val equalsMethodBody = ABlockStmIR()

        // if (ReferenceEquals(null, other)) return false;
        val referenceEqualsNullInvocation = invoke("ReferenceEquals",
                                                   listOf(`null`(recordType), otherIdentifier),
                                                   boolType())

        equalsMethodBody.statements.add(ifThen(referenceEqualsNullInvocation, `return`(false)))

        if (fields.any())
        {
            // if (ReferenceEquals(this, other)) return true;
            val referenceEqualsThisInvocation = invoke("ReferenceEquals",
                                                       listOf(`this`(recordType), otherIdentifier),
                                                       boolType())

            equalsMethodBody.statements.add(ifThen(referenceEqualsThisInvocation, `return`(true)))

            // return firstField == other.firstField && secondField == other.secondField && ...;
            val fieldComparisons = fields.map { compareFields(it, otherIdentifier) }

            if (fields.size > 1)
            {
                val fieldComparisonConjunction = fieldComparisons.drop(2).fold(
                    binaryAnd(fieldComparisons[0], fieldComparisons[1]),
                    { binaryAndsSoFar, fieldComparison -> binaryAnd(binaryAndsSoFar, fieldComparison) })

                equalsMethodBody.statements.add(`return`(fieldComparisonConjunction))
            }
            else
            {
                // TODO return single field comparison without &&.
            }
        }
        else
        {
            // TODO return true immediately without comparing this to other.
        }

        return methodDeclaration("Equals", listOf(otherIdentifier.asParameter()), boolType(), equalsMethodBody)
    }

    // TODO Filter out equality abstraction fields (seems to require an enhancement of the IR).
    private fun significantFieldsOf(record: ARecordDeclIR) = record.fields.map { identifier(it.name, it.type) }

    private fun compareFields(field: SVarExpIR, otherIdentifier: SVarExpIR)
        = binaryEquals(field, invokeGetter(otherIdentifier, field))

    private fun objectEqualsMethod(recordDeclaration: ARecordDeclIR): AMethodDeclIR
    {
        val recordType = recordType(recordDeclaration.name)
        val otherIdentifier = identifier("other", objectType())

        val equalsMethodBody = ABlockStmIR()

        // if (ReferenceEquals(null, other)) return false;
        val referenceEqualsNullInvocation = invoke("ReferenceEquals",
                                                   listOf(`null`(objectType()), otherIdentifier),
                                                   boolType())

        equalsMethodBody.statements.add(ifThen(referenceEqualsNullInvocation, `return`(false)))

        // if (ReferenceEquals(this, other)) return true;
        val referenceEqualsThisInvocation = invoke("ReferenceEquals",
                                                   listOf(`this`(objectType()), otherIdentifier),
                                                   boolType())

        equalsMethodBody.statements.add(ifThen(referenceEqualsThisInvocation, `return`(true)))

        // other is TRecord
        val otherIsOfRecordType = isOfType(otherIdentifier, recordType)

        // Equals((TRecord) other)
        val typeCastOtherToRecordType = typeCast(otherIdentifier, recordType)
        val iEquatableEqualsInvocation = invoke("Equals", listOf(typeCastOtherToRecordType), boolType())

        // return (other is $recordName) && Equals(($recordName) obj);
        equalsMethodBody.statements.add(`return`(binaryAnd(otherIsOfRecordType, iEquatableEqualsInvocation)))

        val objectEqualsMethodDeclaration = methodDeclaration("Equals",
                                                              listOf(otherIdentifier.asParameter()),
                                                              boolType(),
                                                              equalsMethodBody)

        objectEqualsMethodDeclaration.addModifier("override")

        return objectEqualsMethodDeclaration
    }
}
