package org.overture.codegen.vdm2cs.framework.utilities.builders.vdm

abstract class VdmCompositeBuilder() : VdmBuilder
{
    private val _children = mutableListOf<VdmBuilder>()

    val children: List<VdmBuilder>
        get() = _children

    fun <TElement : VdmBuilder> add(element: TElement, initialiser: TElement.() -> Unit = { }): TElement
    {
        element.initialiser()
        _children.add(element)
        return element
    }

    fun combinedDescription(parentDescription: String)
        = (listOf(parentDescription) + children.map { it.description }).joinToString("; and ")
}
