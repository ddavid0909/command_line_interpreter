package commands

import commands.input.CommandInput
import commands.input.StandardCommandInput
import lexer.Token
import commands.output.CommandOutput
import commands.output.StandardCommandOutput

abstract class Command {
    var commandOutput: CommandOutput = StandardCommandOutput()
    var commandInput : CommandInput = StandardCommandInput()
    abstract fun invoke()
    abstract fun parseInput(input: List<Token>)
}