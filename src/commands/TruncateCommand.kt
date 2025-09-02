package commands

import commands.input.CommandInputProvider
import exceptions.syntax.*
import lexer.*
import parser.LiteralTerminal
import parser.Terminal
import java.io.File

class TruncateCommand : Command(){
    override fun invoke() {
        val fileName = this.commandInput.getArgument()
        File(fileName).writeText("")
    }

    override fun parseInput(input: List<Token>) {
        var _input : Terminal? = null

        for (token in input) {
            when(token) {
                is AppendOutputToken -> throw PresentOutputException()
                is CommandToken -> continue
                is InputToken -> throw PresentInputException()
                is NonQuotedToken -> _input = if (_input == null) LiteralTerminal(token.value) else throw MultipleInputException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> throw PresentOutputException()
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }

        this.commandInput = CommandInputProvider.provide(_input)
    }
}