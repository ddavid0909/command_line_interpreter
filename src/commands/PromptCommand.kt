package commands

import commands.input.CommandInputProvider
import exceptions.syntax.MultipleInputException
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
        var input_ : Terminal? = null

        for (token in input) {
            when(token) {
                is AppendOutputToken -> throw PresentOutputException()
                is CommandToken -> continue
                is InputToken -> input_ = if (input_ == null) InputTerminal(token.value) else throw MultipleInputException()
                is NonQuotedToken -> input_ = if (input_ == null) InputTerminal(token.value) else throw MultipleInputException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> throw PresentOutputException()
                is PipelineToken -> continue
                is QuotedToken -> input_ = if (input_ == null) LiteralTerminal(token.value) else throw MultipleInputException()
            }
        }

        this.commandInput = CommandInputProvider.provide(input_)
       // there is no output

    }
}