package org.overture.codegen.vdm2cs.common.fields

sealed class DataFieldEntry(val name: String, val type: String, val isMutable: Boolean)
{
    operator fun component1(): String = name
    operator fun component2(): String = type
    operator fun component3(): Boolean = isMutable

    class DefaultFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)

    class ObjectFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)

    class StringFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)

    class SetFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)

    class SequenceFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)

    class MappingFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)

    class UnionFieldEntry(name: String, type: String, isMutable: Boolean = false) :
        DataFieldEntry(name, type, isMutable)
}
