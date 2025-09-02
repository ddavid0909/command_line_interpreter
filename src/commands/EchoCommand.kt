package commands

import commands.input.CommandInputProvider
import commands.output.CommandOutputProvider
import lexer.*
import parser.*
import exceptions.syntax.*

class EchoCommand : Command() {

    override fun invoke() {
        val inputData = this.commandInput.getArgument()
        commandOutput.output(inputData)
    }

    override fun parseInput(input: List<Token>) {
        var _input : Terminal? = null
        var _output : Terminal? = null

        for (token in input) {
            when (token) {
                is AppendOutputToken -> _output = if (_output == null) AppendOutputTerminal(token.value) else throw MultipleOutputException()
                is CommandToken -> continue
                is InputToken -> _input = if (_input == null) InputTerminal(token.value) else throw MultipleInputException()
                is NonQuotedToken -> _input = if (_input == null) InputTerminal(token.value) else throw MultipleInputException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> _output = if (_output == null) OutputTerminal(token.value) else throw MultipleOutputException()
                is PipelineToken -> continue
                is QuotedToken -> _input = if (_input == null) LiteralTerminal(token.value) else throw MultipleInputException()
            }
        }
        this.commandInput = CommandInputProvider.provide(_input)
        this.commandOutput = CommandOutputProvider.provide(_output)
    }
}