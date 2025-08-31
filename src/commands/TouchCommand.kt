package commands

import commands.input.CommandInputProvider
import exceptions.syntax.PresentInputException
import exceptions.syntax.PresentOptionException
import exceptions.syntax.PresentOutputException
import exceptions.syntax.PresentQuotedException
import lexer.*
import parser.LiteralTerminal
import parser.Terminal
import java.io.File

class TouchCommand : Command() {
    override fun invoke() {
        val fileName = this.commandInput.getArgument()
        val file = File(fileName)
        if (!file.createNewFile()) throw FileAlreadyExistsException(file)
    }

    override fun parseInput(input: List<Token>) {
        val terminals = mutableListOf<Terminal>()

        for (token in input) {
            when(token) {
                is AppendOutputToken -> throw PresentOutputException()
                is CommandToken -> continue
                is InputToken -> throw PresentInputException()
                is NonQuotedToken -> terminals.add(LiteralTerminal(token.value))
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> throw PresentOutputException()
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }

        this.commandInput = CommandInputProvider.provide(terminals)
    }
}