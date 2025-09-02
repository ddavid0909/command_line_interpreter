package lexer

import exceptions.tokens.CommandTokenException
import exceptions.tokens.UnrecognizedTokenException

/**
 * Lexer tokenizes the input, allowing commands to work with it easier
 */
class Lexer(val input: String) {
    var pos = 0
    private var commandTokenNext = true
    private val tokens = mutableListOf<Token>()

    fun tokenize() : List<Token> {
        while (this.pos < this.input.length) {
            if (this.input[this.pos].isWhitespace()) {
                this.pos++
            }
            else if (this.commandTokenNext) {
                this.tokens.add(CommandToken(this))
                this.commandTokenNext = false
            }
            else if (this.input[this.pos] == '"') {
                this.tokens.add(QuotedToken(this))
            }
            else if (this.input[this.pos] == '|') {
                this.tokens.add(PipelineToken(this))
                this.commandTokenNext = true
            }
            else if (this.input[this.pos] == '-') {
                this.tokens.add(OptionToken(this))
            }
            else if (this.input[this.pos] == '<') {
                if (this.pos + 1 < this.input.length && this.input[this.pos+1] == '<') {
                    this.tokens.add(AppendOutputToken(this))
                } else {
                    this.tokens.add(OutputToken(this))}
            }
            else if (this.input[this.pos] == '>') {
                this.tokens.add(InputToken(this))
            }
            else if (this.input[this.pos].isLetter()) {
                this.tokens.add(NonQuotedToken(this))
            }
            else {
                throw UnrecognizedTokenException(this.input, this.pos)
            }
        }

        if (this.commandTokenNext) throw CommandTokenException(this.input, this.input.length)

        return this.tokens
    }

     fun printList() {
        for (element in this.tokens) {
            println(element.value)
        }
    }


}