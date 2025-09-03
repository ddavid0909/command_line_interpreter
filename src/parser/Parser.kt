package parser

import commands.Command
import commands.CommandRegistry
import exceptions.syntax.NoCommandTerminalException
import exceptions.syntax.RedirectionNotLastException
import lexer.*


/**
 * General parser is used to
 * 1. Split tokens into commands.
 * 2. Perform general syntax checks
 * 3. Return a command to execute.
 *
 */
class Parser(private val tokens: List<Token>) {
    private val commandLists = mutableListOf<MutableList<Token>>()

    fun parse(): Command {
        var commandNext = true
        var newList = mutableListOf<Token>()
        for (token in this.tokens) {
            if (commandNext && token.getKind() != "Command") {
                throw NoCommandTerminalException()
            }
            if (newList.isNotEmpty()) this.redirectionLast(newList.last(), token)
            commandNext = false
            if (token is PipelineToken) {
                this.commandLists.add(newList)
                newList = mutableListOf()
                commandNext = true
            } else {
                newList.add(token)
            }
        }
        if (commandNext) throw NoCommandTerminalException()
        this.commandLists.add(newList)
        return CommandRegistry.buildCommand(this.commandLists)
    }

    private fun redirectionLast(lastToken: Token, currentToken: Token) {
        if (lastToken is OutputToken || lastToken is AppendOutputToken || lastToken is InputToken)
            if (currentToken !is OutputToken && currentToken !is AppendOutputToken && currentToken !is InputToken)
                throw RedirectionNotLastException()
    }


}