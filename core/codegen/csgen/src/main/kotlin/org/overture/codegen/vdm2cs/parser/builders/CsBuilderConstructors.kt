package org.overture.codegen.vdm2cs.parser.builders

import org.overture.codegen.vdm2cs.common.DefaultNames.CONTRACT_INVARIANT_METHOD
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry
import org.overture.codegen.vdm2cs.common.fields.DataFieldEntry.*
import org.overture.codegen.vdm2cs.parser.ast.declarations.CsEnumConstantDeclaration
import org.overture.codegen.vdm2cs.utilities.*

//region DOCUMENTS

fun csDocument(definedSymbols: List<String>,
               importedTypes: List<String>,
               staticallyImportedTypes: List<String>,
               namespaceName: String,
               initialiser: CsNamespaceDeclarationBuilder.() -> Unit = { })
    = CsDocumentBuilder()
    .apply {
        definedSymbols.forEach { define(it) }
        importedTypes.forEach { using(it) }
        staticallyImportedTypes.forEach { usingStatic(it) }
        namespace(namespaceName, initialiser)
    }.ast

//endregion

//region CLASSES

fun publicStaticClass(name: String, initialiser: CsClassDeclarationBuilder.() -> Unit = { })
    = CsClassDeclarationBuilder(name,
                                typeParameters = null,
                                superTypes = emptyList(),
                                modifiers = listOf("public", "static"),
                                attributes = emptyList())
    .apply(initialiser).ast

fun publicStaticPartialClass(name: String, initialiser: CsClassDeclarationBuilder.() -> Unit = { })
    = CsClassDeclarationBuilder(name,
                                typeParameters = null,
                                superTypes = emptyList(),
                                modifiers = listOf("public", "static", "partial"),
                                attributes = emptyList())
    .apply(initialiser).ast

fun publicSealedDataClass(name: String, fields: List<DataFieldEntry>, invariants: List<String> = emptyList(),
                          initialiser: CsClassDeclarationBuilder.() -> Unit = { })
    = CsClassDeclarationBuilder(name,
                                typeParameters = null,
                                superTypes = listOf("ICopyable<$name>", "IEquatable<$name>"),
                                modifiers = listOf("public", "sealed"),
                                attributes = emptyList())
    .apply {
        for (field in fields)
        {
            if (field.isMutable) +publicAutoProperty(field.name, field.type)
            else +publicReadonlyAutoProperty(field.name, field.type)
        }

        +dataClassConstructor(enclosingClassName = name, fieldEntries = fields)

        if (invariants.isNotEmpty())
            +contractInvariantMethod(invariants)
    }.apply(initialiser).apply {

    +copyableCopyMethod(enclosingClassName = name, fieldEntries = fields)

    when (fields.size)
    {
        0    -> +emptyEquatableEqualsMethod(enclosingClassName = name)
        else -> +equatableEqualsMethod(enclosingClassName = name, fieldEntries = fields)
    }

    +objectEqualsMethod(enclosingClassName = name)

    +getHashCodeMethod(fieldEntries = fields)
}.ast

private fun dataClassConstructor(enclosingClassName: String, fieldEntries: List<DataFieldEntry>)
    = publicConstructor(className = enclosingClassName,
                        parameters = fieldEntries.map { it.name.toLowerCamelCase() to it.type })
{
    for ((fieldName, fieldType, isMutable) in fieldEntries)
        +assigns(fieldName, fieldName.toLowerCamelCase())
}

private fun contractInvariantMethod(invariants: List<String>)
    = privateMethod(name = CONTRACT_INVARIANT_METHOD,
                    returnType = "void",
                    parameters = emptyList(),
                    attributes = listOf("ContractInvariantMethod"))
{
    for (invariant in invariants)
        +invariant(invariant)
}

private fun copyableCopyMethod(enclosingClassName: String, fieldEntries: List<DataFieldEntry>)
    = publicMethod(name = "Copy",
                   returnType = enclosingClassName,
                   parameters = emptyList())
{
    val formattedFields = fieldEntries.map {
        when (it)
        {
            is DefaultFieldEntry -> it.name
            else                 -> "${it.name}.Copy()"
        }
    }
    +expressionBody("new $enclosingClassName(${formattedFields.joinToString(", ")})")
}

private fun emptyEquatableEqualsMethod(enclosingClassName: String)
    = publicMethod(name = "Equals",
                   returnType = "bool",
                   parameters = listOf("that" to enclosingClassName))
{
    +returns("!ReferenceEquals(null, that)")
}

private fun equatableEqualsMethod(enclosingClassName: String, fieldEntries: List<DataFieldEntry>)
    = publicMethod(name = "Equals", returnType = "bool",
                   parameters = listOf("that" to enclosingClassName))
{
    +ifThen(ifCondition = "ReferenceEquals(null, that)", thenStatement = "return false;")
    +ifThen(ifCondition = "ReferenceEquals(this, that)", thenStatement = "return true;")

    val comparisons = fieldEntries.map {
        when (it)
        {
            is DefaultFieldEntry  -> "${it.name} == that.${it.name}"
            is StringFieldEntry   -> "string.Equals(${it.name}, that.${it.name})"
            is ObjectFieldEntry   -> "object.Equals(${it.name}, that.${it.name})"
            is SetFieldEntry      -> "${it.name}.SetEquals(that.${it.name})"
            is SequenceFieldEntry -> "${it.name}.SequenceEqual(that.${it.name})"
            is MappingFieldEntry  -> "${it.name}.DictionaryEquals(that.${it.name})"
            is UnionFieldEntry    -> "${it.name}.UnionEquals(that.${it.name})"
        }
    }

    +returns(comparisons.joinToString(" && "))
}

private fun objectEqualsMethod(enclosingClassName: String)
    = publicOverrideMethod(name = "Equals",
                           returnType = "bool",
                           parameters = listOf("that" to "object"))
{
    +ifThen(ifCondition = "ReferenceEquals(this, that)", thenStatement = "return true;")
    +returns("that is $enclosingClassName && Equals(($enclosingClassName) that)")
}

private fun getHashCodeMethod(fieldEntries: List<DataFieldEntry>)
    = publicOverrideMethod(name = "GetHashCode",
                           returnType = "int",
                           parameters = emptyList())
{
    +assigns("var result", "17")

    for ((fieldName, fieldType, isMutable) in fieldEntries)
    {
        when (fieldType)
        {
            "bool" -> +assigns("result", "31 * result + ($fieldName ? 1 : 0)")
            "int"  -> +assigns("result", "31 * result + $fieldName")
            else   -> +assigns("result", "31 * result + $fieldName.GetHashCode()")
        }
    }

    +returns("result")
}

//endregion

//region ENUMS

fun publicEnum(name: String, initialiser: CsEnumDeclarationBuilder.() -> Unit = { })
    = CsEnumDeclarationBuilder(name, listOf("public"), emptyList())
    .apply(initialiser).ast

fun enumConstant(name: String, value: String? = null)
    = CsEnumConstantDeclaration(parseName(name), value?.let { parseExpression(it) })

//endregion

//region METHODS

fun method(name: String?,
           returnType: String,
           typeParameters: List<String>? = null,
           parameters: List<Pair<String, String>>,
           modifiers: List<String>,
           attributes: List<String> = emptyList(),
           initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = CsMethodDeclarationBuilder(name, returnType, typeParameters, parameters,
                                 modifiers, attributes)
    .apply(initialiser).ast

fun privateMethod(name: String, returnType: String,
                  typeParameters: List<String>? = null,
                  parameters: List<Pair<String, String>>,
                  attributes: List<String> = emptyList(),
                  initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = method(name, returnType, typeParameters, parameters, listOf("private"), attributes, initialiser)

fun publicMethod(name: String, returnType: String,
                 typeParameters: List<String>? = null,
                 parameters: List<Pair<String, String>>,
                 attributes: List<String> = emptyList(),
                 initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = method(name, returnType, typeParameters, parameters, listOf("public"), attributes, initialiser)

fun publicOverrideMethod(name: String, returnType: String,
                         typeParameters: List<String>? = null,
                         parameters: List<Pair<String, String>>,
                         attributes: List<String> = emptyList(),
                         initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = method(name, returnType, typeParameters, parameters, listOf("public", "override"), attributes, initialiser)

fun publicStaticMethod(name: String, returnType: String,
                       typeParameters: List<String>? = null,
                       parameters: List<Pair<String, String>>,
                       attributes: List<String> = emptyList(),
                       initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = method(name, returnType, typeParameters, parameters, listOf("public", "static"), attributes, initialiser)

fun purePublicStaticMethod(name: String, returnType: String,
                           typeParameters: List<String>? = null,
                           parameters: List<Pair<String, String>>,
                           initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = publicStaticMethod(name, returnType, typeParameters, parameters, listOf("Pure"), initialiser)

fun publicConstructor(className: String, parameters: List<Pair<String, String>>,
                      initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = method(null, className, null, parameters, listOf("public"), emptyList(), initialiser)

fun staticConstructor(className: String, parameters: List<Pair<String, String>>,
                      initialiser: CsMethodDeclarationBuilder.() -> Unit = { })
    = method(null, className, null, parameters, listOf("static"), emptyList(), initialiser)

fun implicitCastOperatorViaInstantiation(fromType: String, toType: String)
    = method(null, toType, null, listOf("that" to fromType),
             listOf("public", "static", "implicit", "operator"), emptyList())
{
    +expressionBody("new $toType(that)")
}

fun implicitCastOperatorViaValueProperty(fromType: String, toType: String)
    = method(null, toType, null, listOf("that" to fromType),
             listOf("public", "static", "implicit", "operator"), emptyList())
{
    +expressionBody("that.Value")
}

//endregion

//region PROPERTIES

fun property(name: String, type: String, modifiers: List<String>,
             initialiser: CsPropertyDeclarationBuilder.() -> Unit)
    = CsPropertyDeclarationBuilder(name, type, modifiers)
    .apply(initialiser).ast

fun publicProperty(name: String, type: String, initialiser: CsPropertyDeclarationBuilder.() -> Unit = { })
    = property(name, type, listOf("public"), initialiser)

fun publicAutoProperty(name: String, type: String)
    = publicProperty(name, type)
{
    publicAutoGetter()
    publicAutoSetter()
}

fun publicReadonlyAutoProperty(name: String, type: String)
    = publicProperty(name, type)
{
    publicAutoGetter()
}

fun publicStaticInitialisedReadonlyAutoProperty(name: String, type: String, initialiser: String)
    = property(name, type, listOf("public", "static"))
{
    publicAutoGetter()
    initialiser(initialiser)
}

fun privateStaticAutoProperty(name: String, type: String, initialiser: String? = null)
    = property(name, type, listOf("private", "static"))
{
    publicAutoGetter()
    publicAutoSetter()
    if (initialiser != null) initialiser(initialiser)
}

//endregion
