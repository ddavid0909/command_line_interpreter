package commands

import exceptions.UnknownCommandException
import lexer.Token

object CommandRegistry {
    private val commands = mutableMapOf<String, () -> Command>()

    init {
        registerCommand("echo") { EchoCommand() }
        registerCommand("date") { DateCommand() }
        registerCommand("time") { TimeCommand() }
        registerCommand("prompt") { PromptCommand() }
        registerCommand("touch") { TouchCommand() }
        registerCommand("truncate") { TruncateCommand() }
        registerCommand("rm") { RemoveCommand() }
        registerCommand("wc") { WordCountCommand() }
        registerCommand("head") { HeadCommand() }
        registerCommand("batch") { BatchCommand() }
        registerCommand("tr") { TranslateCommand() }
    }

    private fun registerCommand(name: String, factory: () -> Command) {
        this.commands[name] = factory
    }

    fun getCommand(name: String): Command {
        val returnValue = this.commands[name]
        returnValue ?: throw UnknownCommandException(name)
        return returnValue.invoke()
    }

    fun buildCommand(commands: MutableList<MutableList<Token>>): Command {
        var command = getCommand(commands[0][0].value)
        command.parseInput(commands[0])
        if (commands.size > 1) {
            command = PipelineCommand()
            command.set(commands)
        }

        return command
    }
}