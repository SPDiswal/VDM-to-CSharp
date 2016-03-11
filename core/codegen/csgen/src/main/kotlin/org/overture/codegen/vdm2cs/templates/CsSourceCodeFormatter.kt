package org.overture.codegen.vdm2cs.templates

/**
 * The C# source code formatter fixes the indentation of C# source code and removes redundant blank lines.
 */
final class CsSourceCodeFormatter
{
    fun format(input: String): String
    {
        val result = FormattingResult()

        for (line in input.lines().map { it.trim() })
        {
            when (line)
            {
                "{"  ->
                {
                    result.add(line)
                    result.indent()
                }

                "}"  ->
                {
                    while (result.lines.last().isBlank())
                        result.removeLast()

                    result.dedent()
                    result.add(line)
                    result.addBlank()
                }

                else ->
                {
                    if (!line.isBlank() || (result.lines.last().trim() != "{" && !result.lines.last().isBlank()))
                        result.add(line)
                }
            }
        }

        return result.joinedLines
    }

    private class FormattingResult
    {
        private val indent = "    "

        private var currentIndentationLevel = 0

        private val currentIndent: String
            get() = indent.repeat(currentIndentationLevel)

        fun indent() = currentIndentationLevel++

        fun dedent() = currentIndentationLevel--

        fun add(line: String) = lines.add(currentIndent + line)

        fun addBlank() = lines.add(currentIndent)

        fun removeLast() = lines.removeAt(lines.lastIndex)

        val lines = mutableListOf<String>()

        val joinedLines: String
            get() = lines.joinToString("\n")
    }
}
