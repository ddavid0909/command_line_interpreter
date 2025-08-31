package commands

import lexer.*
import parser.*
import exceptions.syntax.*
import commands.output.CommandOutputProvider
import java.time.LocalDate

class DateCommand : Command() {
    override fun invoke() {
        this.commandOutput.output(LocalDate.now().toString())
    }

    override fun parseInput(input: List<Token>) {
        val terminals: MutableList<Terminal> = mutableListOf()

        for (token in input) {
            when(token) {
                is AppendOutputToken -> terminals.add(AppendOutputTerminal(token.value))
                is CommandToken -> continue
                is InputToken -> throw PresentInputException()
                is NonQuotedToken -> throw PresentNonQuotedException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> terminals.add(OutputTerminal(token.value))
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }
        this.commandOutput = CommandOutputProvider.provide(terminals.filter { it is OutputTerminal || it is AppendOutputTerminal })
    }
}