package commands

import commands.input.CommandInputProvider
import commands.output.CommandOutputProvider
import commands.options.word_count.CStrategy
import commands.options.word_count.WStrategy
import commands.options.word_count.Strategy
import exceptions.syntax.*
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
        var _input : Terminal? = null
        var _output : Terminal? = null

        for (token in input) {
            when(token) {
                is AppendOutputToken -> _output = if(_output == null) AppendOutputTerminal(token.value) else throw MultipleOutputException()
                is CommandToken -> continue
                is InputToken -> _input = if (_input == null) InputTerminal(token.value) else throw MultipleInputException()
                is NonQuotedToken -> _input = if (_input == null) InputTerminal(token.value) else throw MultipleInputException()
                is OptionToken -> this.option = if (this.option == null) token.value else throw MultipleOptionException()
                is OutputToken -> _output = if (_output == null) OutputTerminal(token.value) else throw MultipleOutputException()
                is PipelineToken -> continue
                is QuotedToken -> _input = if (_input == null) LiteralTerminal(token.value) else throw MultipleInputException()
            }
        }

        this.commandInput = CommandInputProvider.provide(_input)
        this.commandOutput = CommandOutputProvider.provide(_output)

    }
}