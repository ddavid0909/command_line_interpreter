package commands


import commands.options.head.NStrategy
import commands.options.head.Strategy
import lexer.*
import parser.AppendOutputTerminal
import parser.InputTerminal
import parser.Terminal

class HeadCommand : Command() {
    private val strategies = mutableMapOf<String, Strategy>("n" to NStrategy())
    private var option : String? = null
    override fun invoke() {
        TODO("Not yet implemented")
    }

    override fun parseInput(input: List<Token>) {
        val terminals = mutableListOf<Terminal>()
        for (token in input) {
            when (token) {
                is AppendOutputToken -> terminals.add(AppendOutputTerminal(token.value))
                is CommandToken -> continue
                is InputToken -> terminals.add(InputTerminal(token.value))
                is NonQuotedToken -> terminals.add(InputTerminal(token.value))
                is OptionToken -> TODO()
                is OutputToken -> TODO()
                is PipelineToken -> TODO()
                is QuotedToken -> TODO()
            }
        }
    }
}