package commands

import exceptions.UnknownCommandException
import lexer.Token

object CommandRegistry {
    private val commands = mutableMapOf<String, ()->Command>()

    fun registerCommand(name: String,  factory: () -> Command) {
        this.commands[name] = factory
    }

    private fun getCommand(name: String) : Command {
        val returnValue = this.commands[name]
        returnValue ?: throw UnknownCommandException(name)
        return returnValue.invoke()
    }

    fun buildCommand(commands: List<List<Token>>) : Command {
        var command  = getCommand(commands[0][0].value)
        command.parseInput(commands[0])

        if (commands.size > 1) {
            command = PipelineCommand()
            for (list in commands) {
                val newCommand = getCommand(list[0].value)
                // PipelineCommand must add temp input and output before passing occurs.
                newCommand.parseInput(list)
                command.add(newCommand)
            }
        }

        return command
    }
}