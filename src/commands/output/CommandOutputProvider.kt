package commands.output

import exceptions.FatalError
import exceptions.syntax.MultipleOutputException
import lexer.*
import parser.AppendOutputTerminal
import parser.OutputTerminal
import parser.Terminal

object CommandOutputProvider {
    fun provide(terminals : List<Terminal>) : CommandOutput {
        if (terminals.size > 1) throw MultipleOutputException()
        if (terminals.isEmpty()) return StandardCommandOutput()
        return when(val token = terminals[0]) {
            is OutputTerminal -> FileCommandOutput(token.value)
            is AppendOutputTerminal -> AppendCommandOutput(token.value)
            else -> throw FatalError()
        }
    }
}
