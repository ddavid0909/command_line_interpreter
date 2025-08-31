package commands.input

import exceptions.FatalError
import exceptions.syntax.MultipleInputException
import parser.*

object CommandInputProvider {
    fun provide(terminals : List<Terminal>) : CommandInput {
        if (terminals.size > 1) throw MultipleInputException()
        if (terminals.isEmpty()) return StandardCommandInput()
        return when(val terminal = terminals[0]) {
            is InputTerminal -> FileCommandInput(terminal.value)
            is LiteralTerminal -> QuotedCommandInput(terminal.value)
            else -> throw FatalError()
        }
    }
}