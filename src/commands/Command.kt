package commands

import commands.input.CommandInput
import commands.input.StandardCommandInput
import lexer.Token
import commands.output.CommandOutput
import commands.output.StandardCommandOutput
import exceptions.syntax.MultipleInputException
import exceptions.syntax.MultipleOptionException
import exceptions.syntax.MultipleOutputException
import parser.Terminal

abstract class Command {
    var commandOutput: CommandOutput = StandardCommandOutput()
    var commandInput : CommandInput = StandardCommandInput()

    var myInputTerminal : Terminal? = null
        set(value) {
            if (field != null) throw MultipleInputException()
            field = value
        }
    var myOutputTerminal: Terminal? = null
        set(value) {
            if (field != null) throw MultipleOutputException()
            field = value
        }
    var myOptionTerminal: Terminal? = null
        set(value) {
            if (field != null) throw MultipleOptionException()
            field = value
        }

    abstract fun invoke()
    abstract fun parseInput(input: List<Token>)


}