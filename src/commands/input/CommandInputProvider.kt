package commands.input

import exceptions.FatalError
import parser.Terminal
import parser.InputTerminal
import parser.LiteralTerminal

object CommandInputProvider {
    fun provide(terminal : Terminal?) : CommandInput {
        terminal ?: return StandardCommandInput()
        return when(terminal) {
            is InputTerminal -> FileCommandInput(terminal.value)
            is LiteralTerminal -> QuotedCommandInput(terminal.value)
            else -> throw FatalError()
        }
    }
}