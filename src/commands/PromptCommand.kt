package commands

import commands.input.CommandInputProvider
import exceptions.syntax.PresentOptionException
import exceptions.syntax.PresentOutputException
import input.InputManager
import lexer.*
import parser.InputTerminal
import parser.LiteralTerminal
import parser.Terminal

class PromptCommand : Command() {
    override fun invoke() {
        InputManager.setStartSign(this.commandInput.getArgument())
    }

    override fun parseInput(input: List<Token>) {
        val terminals = mutableListOf<Terminal>()

        for (token in input) {
            when(token) {
                is AppendOutputToken -> throw PresentOutputException()
                is CommandToken -> continue
                is InputToken -> terminals.add(InputTerminal(token.value))
                is NonQuotedToken -> terminals.add(InputTerminal(token.value))
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> throw PresentOutputException()
                is PipelineToken -> continue
                is QuotedToken -> terminals.add(LiteralTerminal(token.value))
            }
        }

        this.commandInput = CommandInputProvider.provide(terminals)
       // there is no output

    }
}