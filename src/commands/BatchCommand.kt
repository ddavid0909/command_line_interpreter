package commands

import commands.input.CommandInputProvider
import exceptions.syntax.MultipleInputException
import exceptions.syntax.PresentOptionException
import exceptions.syntax.PresentOutputException
import exceptions.syntax.PresentQuotedException
import lexer.*
import parser.InputTerminal
import parser.Parser
import parser.Terminal

class BatchCommand : Command() {
    override fun invoke() {
        val text = this.commandInput.getArgument().split("\n")
        val commands = mutableListOf<Command>()
        for (line in text) {
            try {
                val lexer = Lexer(line)
                val list = lexer.tokenize()
                val parser = Parser(list)
                commands.add(parser.parse())
            } catch (e: Exception) {
                println("ERROR IN BATCH: ${e.message}")
            }
        }
        for (command in commands) {
            try {
                command.invoke()
            } catch (e: Exception) {
                println("ERROR IN BATCH: ${e.message}")
            }
        }
    }

    override fun parseInput(input: List<Token>) {
        var _input: Terminal? = null

        for (token in input) {
            when (token) {
                is AppendOutputToken -> throw PresentOutputException()
                is CommandToken -> continue
                is InputToken -> _input =
                    if (_input == null) InputTerminal(token.value) else throw MultipleInputException()

                is NonQuotedToken -> _input =
                    if (_input == null) InputTerminal(token.value) else throw MultipleInputException()

                is OptionToken -> throw PresentOptionException()
                is OutputToken -> throw PresentOutputException()
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }

        this.commandInput = CommandInputProvider.provide(_input)
    }
}