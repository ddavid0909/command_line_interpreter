package commands

import commands.input.CommandInputProvider
import commands.output.CommandOutputProvider
import exceptions.syntax.*
import lexer.*
import parser.*

class TranslateCommand : Command() {
    private var with: String = ""
    private var what: String = ""

    override fun invoke() {
        this.commandOutput.output(this.commandInput.getArgument().replace(this.what, this.with))
    }

    override fun parseInput(input: List<Token>) {
        var _input: Terminal? = null
        var _output: Terminal? = null
        val literals = mutableListOf<Terminal>()

        for (token in input) {
            when (token) {
                is AppendOutputToken -> _output =
                    if (_output == null) AppendOutputTerminal(token.value) else throw MultipleOutputException()

                is CommandToken -> continue
                is InputToken -> _input =
                    if (_input == null) InputTerminal(token.value) else throw MultipleInputException()

                is NonQuotedToken -> _input =
                    if (_input == null) InputTerminal(token.value) else throw MultipleInputException()

                is OptionToken -> throw PresentOptionException()
                is OutputToken -> _output =
                    if (_output == null) OutputTerminal(token.value) else throw MultipleOutputException()

                is PipelineToken -> continue
                is QuotedToken -> literals.add(LiteralTerminal(token.value))
            }
        }

        when (literals.size) {
            0 -> throw NoLiteralTerminalException()
            1 -> this.what = literals[0].value
            2 -> {
                this.what = literals[0].value
                this.with = literals[1].value
            }

            3 -> {
                _input = literals[0]
                this.what = literals[1].value
                this.with = literals[2].value
            }

            else -> throw MultipleLiteralException()
        }
        this.commandInput = CommandInputProvider.provide(_input)
        this.commandOutput = CommandOutputProvider.provide(_output)
    }
}