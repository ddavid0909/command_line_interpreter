package parser

import commands.Command
import commands.CommandRegistry
import lexer.PipelineToken
import lexer.Token


/**
 * General parser is used to
 * 1. Split tokens into commands.
 * 2. Perform general syntax checks
 * 3. Return a command to execute.
 *
 */
class Parser(private val tokens:List<Token>) {
    private val commandLists = mutableListOf<MutableList<Token>>()

    fun parse() : Command {
        var newList = mutableListOf<Token>()
        for (token in this.tokens) {
            if (token is PipelineToken) {
                this.commandLists.add(newList)
                newList = mutableListOf()
            } else {
                newList.add(token)
            }
        }
        this.commandLists.add(newList)
        return CommandRegistry.buildCommand(this.commandLists)
    }



}