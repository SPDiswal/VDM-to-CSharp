package org.overture.codegen.vdm2cs.framework.utilities

object SpekDescriptions
{
    fun formatParameters(parameters: List<Pair<String, String>>)
        = formatItems(parameters.map { it.second }, "parameter")

    fun formatItems(items: List<String>,
                    singularDescription: String,
                    pluralDescription: String = "${singularDescription}s",
                    preposition: String = "of",
                    article: String = "a") = when (items.size)
    {
        0    -> "no $pluralDescription"
        1    -> "$article $singularDescription $preposition '${items.first()}'"
        else ->
        {
            val joinedItems = items.dropLast(1).map { "'$it'" }.joinToString(", ")
            "$pluralDescription $preposition $joinedItems and '${items.last()}'"
        }
    }
}
