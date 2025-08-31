package commands

import commands.output.CommandOutputProvider
import exceptions.syntax.PresentInputException
import exceptions.syntax.PresentNonQuotedException
import exceptions.syntax.PresentOptionException
import exceptions.syntax.PresentQuotedException
import lexer.*
import parser.AppendOutputTerminal
import parser.Terminal
import java.time.LocalTime

class TimeCommand : Command() {
    override fun invoke() {
        this.commandOutput.output(LocalTime.now().toString())
    }

    override fun parseInput(input: List<Token>) {
        val terminals = mutableListOf<Terminal>()

        for (token in input) {
            when (token) {
                is AppendOutputToken -> terminals.add(AppendOutputTerminal(token.value))
                is CommandToken -> continue
                is InputToken -> throw PresentInputException()
                is NonQuotedToken -> throw PresentNonQuotedException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> terminals.add(AppendOutputTerminal(token.value))
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }
        this.commandOutput = CommandOutputProvider.provide(terminals)
    }
}