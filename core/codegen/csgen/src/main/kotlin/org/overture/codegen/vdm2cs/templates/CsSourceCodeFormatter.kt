package org.overture.codegen.vdm2cs.templates

/**
 * The C# source code formatter fixes the indentation of C# source code and removes redundant blank lines.
 */
final class CsSourceCodeFormatter
{
    fun format(input: String): String
    {
        val result = FormattingResult()

        for ((index, line) in input.lines().map { it.trim() }.withIndex())
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
                    removeLatestBlankLines(result)

                    result.dedent()
                    result.add(line)
                    result.addBlank()
                }

                else ->
                {
                    val isNamespaceDeclaration = line.matches("""namespace \w*$""".toRegex())
                    val isContractDirective = line.matches("""Contract\.(?:Requires|Ensures)\(.*\);""".toRegex())
                    val isSignificant = !line.isBlank()
                                        || result.lines.last().trim() != "{" && !result.lines.last().isBlank()

                    if (isNamespaceDeclaration && index > 0)
                        result.addBlank()

                    if (isContractDirective)
                        removeLatestBlankLines(result)

                    if (isSignificant)
                        result.add(line)

                    if (isContractDirective)
                        result.addBlank()
                }
            }
        }

        return result.joinedLines
    }

    private fun removeLatestBlankLines(result: FormattingResult)
    {
        while (result.lines.last().isBlank())
            result.removeLast()
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
