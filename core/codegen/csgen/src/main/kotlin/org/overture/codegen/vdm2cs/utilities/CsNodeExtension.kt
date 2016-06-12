package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.vdm2cs.parser.ast.expressions.primary.*
import org.overture.codegen.vdm2cs.parser.ast.identifiers.CsCommonIdentifier
import org.overture.codegen.vdm2cs.parser.ast.statements.*

val CsStatement.isContractDirective: Boolean
    get() = this is CsExpressionStatement &&
            expression is CsCallExpression &&
            expression.receiver is CsMemberExpression &&
            expression.receiver.receiver is CsNameIdentifierExpression &&
            expression.receiver.receiver.identifier == CsCommonIdentifier("Contract")

val CsStatement.uncoverBlocks: CsStatement
    get() = when
    {
        this is CsBlockStatement && this.statements.size == 1 -> this.statements.first().uncoverBlocks
        else                                                  -> this
    }

val CsStatement.isEmptyBlock: Boolean
    get() = this is CsBlockStatement &&
            (this.statements.isEmpty() || this.statements.all { it.isEmptyBlock })

val CsStatement.isRedundantBlock: Boolean
    get() = this is CsBlockStatement && this.statements.isRedundantBlockOnly

val List<CsStatement>.isRedundantBlockOnly: Boolean
    get() = this.size == 1 && this.single() is CsBlockStatement

val CsStatement.isNestedIf: Boolean
    get() = this.uncoverBlocks is CsIfStatement

val CsStatement.isSimpleBlock: Boolean
    get()
    {
        val uncovered = this.uncoverBlocks

        return uncovered is CsReturnStatement ||
               uncovered is CsThrowStatement ||
               uncovered is CsExpressionStatement && (uncovered.expression is CsNameIdentifierExpression ||
                                                      uncovered.expression is CsCallExpression ||
                                                      uncovered.expression is CsMemberExpression ||
                                                      uncovered.expression is CsIndexerExpression)
    }
