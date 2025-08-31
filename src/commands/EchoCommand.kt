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
        val terminals: MutableList<Terminal> = mutableListOf()

        for (token in input) {
            when (token) {
                is AppendOutputToken -> terminals.add(AppendOutputTerminal(token.value))
                is CommandToken -> continue
                is InputToken -> terminals.add(InputTerminal(token.value))
                is NonQuotedToken -> terminals.add(InputTerminal(token.value))
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> terminals.add(OutputTerminal(token.value))
                is PipelineToken -> continue
                is QuotedToken -> terminals.add(LiteralTerminal(token.value))
            }
        }
        this.commandInput = CommandInputProvider.provide(terminals.filter { it is InputTerminal || it is LiteralTerminal })
        this.commandOutput = CommandOutputProvider.provide(terminals.filter { it is OutputTerminal || it is AppendOutputTerminal })
    }
}