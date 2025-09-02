package commands

import exceptions.FatalError
import lexer.InputToken
import lexer.OutputToken
import lexer.Token
import java.io.File

class PipelineCommand() : Command(){
    val commands : MutableList<Command> = mutableListOf()
    private fun add (command : Command) {
        this.commands.add(command)
    }

    override fun invoke() {
        try {
            for (command in this.commands) {
                command.invoke()
            }
        }
        catch (e: Exception) {
            throw Exception("(IN PIPELINE) ${e.message}")
        }
        val dir = File(".temp")
        if (dir.exists()) {
            dir.deleteRecursively()
        }
    }

    override fun parseInput(input: List<Token>) {}

    fun set(commands : List<MutableList<Token>>) {
        val number = commands.size
        val tempFiles = mutableMapOf<Int, String>()
        this.recreateDirectory(".temp")
        for (i in 0 until number) {
            tempFiles.put(i, ".temp/temp_file_$i")
        }
        for ((i, list) in commands.withIndex()) {
            if (i != 0) {
                list.add(InputToken(tempFiles[i-1] ?: throw FatalError()))
            }
            if (i != number-1) {
                list.add(OutputToken(tempFiles[i] ?: throw FatalError()))
            }
            val newCommand = CommandRegistry.getCommand(list[0].value)
            try {
                newCommand.parseInput(list)
            } catch (e: Exception) {
                throw Exception("Command '${list[0].value}' in pipeline - ${e.message}")
            }
            this.add(newCommand)
        }
    }

    private fun recreateDirectory(path: String) {
        val dir = File(path)

        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()
    }
}