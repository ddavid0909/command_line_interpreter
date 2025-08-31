package commands

import commands.input.CommandInputProvider
import commands.output.CommandOutputProvider
import commands.options.word_count.CStrategy
import commands.options.word_count.WStrategy
import commands.options.word_count.Strategy
import exceptions.syntax.MultipleOptionException
import exceptions.syntax.NoOptionTokenException
import exceptions.syntax.NonExistentOptionException
import lexer.*
import parser.*

class WordCountCommand : Command() {
    private val strategies = mutableMapOf<String, Strategy>("c" to CStrategy(), "w" to WStrategy())
    private var option : String? = null
    override fun invoke() {
        val input : String = this.commandInput.getArgument()

        if (this.option == null) throw NoOptionTokenException()
        if (this.strategies[this.option] == null) throw NonExistentOptionException(this.option)

        this.commandOutput.output(this.strategies[this.option]!!.count(input).toString())
    }

    override fun parseInput(input: List<Token>) {
        val terminals = mutableListOf<Terminal>()

        for (token in input) {
            when(token) {
                is AppendOutputToken -> terminals.add(AppendOutputTerminal(token.value))
                is CommandToken -> continue
                is InputToken -> terminals.add(InputTerminal(token.value))
                is NonQuotedToken -> terminals.add(InputTerminal(token.value))
                is OptionToken -> this.option = if (this.option == null) token.value else throw MultipleOptionException()
                is OutputToken -> terminals.add(OutputTerminal(token.value))
                is PipelineToken -> continue
                is QuotedToken -> terminals.add(LiteralTerminal(token.value))
            }
        }

        this.commandInput = CommandInputProvider.provide(terminals.filter {it is LiteralTerminal || it is InputTerminal})
        this.commandOutput = CommandOutputProvider.provide(terminals.filter {it is OutputTerminal || it is AppendOutputTerminal})

    }
}