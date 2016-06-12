package org.overture.codegen.vdm2cs.utilities

import org.overture.codegen.ir.*
import org.overture.codegen.ir.declarations.*
import org.overture.codegen.ir.name.ATypeNameIR
import org.overture.codegen.ir.types.AVoidTypeIR
import org.overture.codegen.vdm2cs.common.Remark.*

fun flatSpecification(definitions: List<SDeclIR>) = AModuleDeclIR().apply {
    this.name = "DEFAULT"
    this.isFlat = true
    this.decls.addAll(definitions.map { it.clone() })
}

fun module(name: String,
           exports: AModuleExportsIR? = null,
           imports: AModuleImportsIR? = null,
           definitions: List<SDeclIR>) = AModuleDeclIR().apply {
    this.name = name
    this.exports = exports
    this.import = imports
    this.isFlat = false
    this.decls.addAll(definitions.map { it.clone() })
}

fun namedTypeDeclaration(qualifier: String? = null, name: String, type: STypeIR,
                         invariant: Pair<String, SExpIR>? = null)
    = ATypeDeclIR().apply {
    this.decl = ANamedTypeDeclIR().apply { this.name = typeName(qualifier, name); this.type = type.clone() }
    this.inv = invariant?.let {
        contractDeclaration(
            name = "inv_$name",
            parameters = listOf(parameter(it.first, type.clone())),
            predicate = it.second.clone()
        )
    }
}

fun recordTypeDeclaration(name: String, fields: List<AFieldDeclIR>, invariant: Pair<String, SExpIR>? = null)
    = ATypeDeclIR().apply {
    this.decl = ARecordDeclIR().apply { this.name = name; this.fields.addAll(fields.map { it.clone() }) }
    this.inv = invariant?.let {
        contractDeclaration(
            name = "inv_$name",
            parameters = listOf(parameter(it.first, recordType(name = name))),
            predicate = it.second.clone()
        )
    }
}

fun field(name: String, type: STypeIR)
    = AFieldDeclIR().apply { this.name = name; this.type = type.clone() }

fun valueDeclaration(name: String, type: STypeIR, value: SExpIR) = AFieldDeclIR().apply {
    this.name = name
    this.type = type.clone()
    this.initial = value.clone()
    this.final = true
}

fun typeName(qualifier: String? = null, name: String)
    = ATypeNameIR().apply { this.definingClass = qualifier; this.name = name }

fun functionDeclaration(name: String, resultName: String = "RESULT", resultType: STypeIR,
                        parameters: List<AFormalParamLocalParamIR>,
                        typeParameters: List<String> = emptyList(),
                        precondition: SExpIR? = null, postcondition: SExpIR? = null, body: SExpIR? = null)
    = AFuncDeclIR().apply {
    this.name = name
    this.formalParams.addAll(parameters.map { it.clone() })
    this.methodType = methodType(parameters.map { it.type.clone() }, resultType.clone())
    this.templateTypes.addAll(typeParameters.map { templateType(it) })
    this.preCond = precondition?.let {
        contractDeclaration(name = "pre_$name",
                            parameters = parameters,
                            predicate = precondition)
    }
    this.postCond = postcondition?.let {
        contractDeclaration(name = "post_$name",
                            parameters = parameters +
                                         parameter(resultName, resultType).apply { this.pattern.addRemark(RESULT) },
                            predicate = postcondition)
    }
    this.body = body?.clone() ?: isNotYetSpecifiedExpression
    this.implicit = body == null
}

fun operationDeclaration(name: String, resultName: String = "RESULT", resultType: STypeIR,
                         parameters: List<AFormalParamLocalParamIR>, typeParameters: List<String> = emptyList(),
                         precondition: SExpIR? = null, postcondition: SExpIR? = null, body: SStmIR? = null,
                         state: Pair<String, STypeIR>? = null,
                         isPure: Boolean = false)
    = AMethodDeclIR().apply {
    this.name = name
    this.formalParams.addAll(parameters.map { it.clone() })
    this.methodType = when (isPure)
    {
        true  -> pureMethodType(parameters.map { it.type.clone() }, resultType.clone())
        false -> methodType(parameters.map { it.type.clone() }, resultType.clone())
    }
    this.templateTypes.addAll(typeParameters.map { templateType(it) })
    this.preCond = precondition?.let {
        contractDeclaration(
            name = "pre_$name",
            parameters = (parameters +
                          state?.let {
                              parameter(state.first, state.second).apply { this.pattern.addRemark(STATE) }
                          }).filterNotNull()
            ,
            predicate = precondition)
    }
    this.postCond = postcondition?.let {
        contractDeclaration(
            name = "post_$name",
            parameters = (parameters +
                          (if (resultType !is AVoidTypeIR)
                              parameter(resultName, resultType).apply { this.pattern.addRemark(RESULT) }
                          else null) +
                          state?.let {
                              parameter("_${state.first}", state.second).apply { this.pattern.addRemark(PRE_STATE) }
                          } +
                          state?.let {
                              parameter(state.first, state.second).apply { this.pattern.addRemark(POST_STATE) }
                          }).filterNotNull(),
            predicate = postcondition)
    }
    this.body = body?.clone() ?: isNotYetSpecifiedStatement
    this.implicit = body == null
}

private fun contractDeclaration(name: String, parameters: List<AFormalParamLocalParamIR>,
                                predicate: SExpIR)
    = AFuncDeclIR().apply {
    this.name = name
    this.formalParams.addAll(parameters.map { it.clone() })
    this.methodType = methodType(parameters.map { it.type.clone() }, boolType)
    this.body = predicate.clone()
    this.implicit = false
}

fun parameter(name: String, type: STypeIR) = AFormalParamLocalParamIR().apply {
    this.pattern = identifierPattern(name)
    this.type = type.clone()
}

fun stateDeclaration(name: String, fields: List<AFieldDeclIR>,
                     invariant: Pair<String, SExpIR>? = null,
                     initialiser: SExpIR? = null)
    = AStateDeclIR().apply {
    this.name = name
    this.fields.addAll(fields.map { it.clone() })
    this.invDecl = invariant?.let {
        contractDeclaration(
            name = "inv_$name",
            parameters = listOf(parameter(it.first, recordType(name = name))),
            predicate = it.second.clone()
        )
    }
    this.initExp = initialiser?.clone()
}

fun variable(name: String, type: STypeIR, value: SExpIR) = AVarDeclIR().apply {
    this.pattern = identifierPattern(name)
    this.type = type.clone()
    this.exp = value.clone()
}
