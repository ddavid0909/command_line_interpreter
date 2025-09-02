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
        var _output : Terminal? = null

        for (token in input) {
            when(token) {
                is AppendOutputToken -> _output = if (_output == null) AppendOutputTerminal(token.value) else throw MultipleOutputException()
                is CommandToken -> continue
                is InputToken -> throw PresentInputException()
                is NonQuotedToken -> throw PresentNonQuotedException()
                is OptionToken -> throw PresentOptionException()
                is OutputToken -> _output = if (_output == null) OutputTerminal(token.value) else throw MultipleOutputException()
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }
        this.commandOutput = CommandOutputProvider.provide(_output)
    }
}