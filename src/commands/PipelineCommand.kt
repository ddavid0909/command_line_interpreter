package commands

import lexer.Token

class PipelineCommand() : Command(){
    val commands : MutableList<Command> = mutableListOf()
    fun add (command : Command) {
        this.commands.add(command)
    }

    override fun invoke() {
        TODO("Not yet implemented")
    }

    override fun parseInput(input: List<Token>) {
        TODO("Not yet implemented")
    }
}