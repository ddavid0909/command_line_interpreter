package commands.output

import exceptions.FatalError
import parser.AppendOutputTerminal
import parser.OutputTerminal
import parser.Terminal

object CommandOutputProvider {
    fun provide(terminal : Terminal?) : CommandOutput {
        terminal ?: return StandardCommandOutput()
        return when(terminal) {
            is OutputTerminal -> FileCommandOutput(terminal.value)
            is AppendOutputTerminal -> AppendCommandOutput(terminal.value)
            else -> throw FatalError()
        }
    }
}
