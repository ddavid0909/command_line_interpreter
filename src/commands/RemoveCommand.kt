package commands

import commands.input.CommandInputProvider
import exceptions.syntax.*
import lexer.*
import parser.LiteralTerminal
import parser.Terminal
import java.io.File

class RemoveCommand : Command() {
    override fun invoke() {
        val fileName = this.commandInput.getArgument()
        val file = File(fileName)
        file.delete()
    }

    override fun parseInput(input: List<Token>) {
        var input_ : Terminal? = null

        for (token in input) {
            when(token) {
                is AppendOutputToken -> throw PresentOutputException()
                is CommandToken -> continue
                is InputToken -> throw PresentInputException()
                is NonQuotedToken -> input_ = if (input_ == null) LiteralTerminal(token.value) else throw MultipleInputException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> throw PresentOutputException()
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }

        this.commandInput = CommandInputProvider.provide(input_)
    }
}