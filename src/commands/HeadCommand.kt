package commands


import com.sun.jdi.connect.Connector.Argument
import commands.input.CommandInputProvider
import commands.options.head.NStrategy
import commands.options.head.Strategy
import commands.output.CommandOutputProvider
import exceptions.syntax.MultipleInputException
import exceptions.syntax.MultipleOutputException
import exceptions.syntax.NonExistentOptionException
import exceptions.syntax.PresentQuotedException
import lexer.*
import parser.*

class HeadCommand : Command() {
    private val strategies = mutableMapOf<String, Strategy>("n" to NStrategy())
    private var strategy : Strategy? = null
    override fun invoke() {
        TODO("Not yet implemented")
    }

    override fun parseInput(input: List<Token>) {
        var _output : Terminal? = null
        var _input : Terminal? = null
        //var _option: Terminal? = null

        for (token in input) {
            when (token) {
                is AppendOutputToken -> _output = if (_output == null) AppendOutputTerminal(token.value) else throw MultipleOutputException()
                is CommandToken -> continue
                is InputToken -> _input = if( _input == null) InputTerminal(token.value) else throw MultipleInputException()
                is NonQuotedToken -> _input = if(_input == null) InputTerminal(token.value) else throw MultipleInputException()
                is OptionToken -> {
                    val option = token.value.substring(0,1)
                    this.strategy = this.strategies[option]
                    this.strategy ?: throw NonExistentOptionException(option)
                }
                is OutputToken -> _output = if (_output == null) OutputTerminal(token.value) else throw MultipleOutputException()
                is PipelineToken -> continue
                is QuotedToken -> throw PresentQuotedException()
            }
        }

        this.commandInput = CommandInputProvider.provide(_input)
        this.commandOutput = CommandOutputProvider.provide(_output)
    }
}