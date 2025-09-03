package lexer

import exceptions.tokens.UnrecognizedTokenException

/**
 * Lexer tokenizes the input, allowing commands to work with it easier.
 * Only sequence-based check is command after pipeline, as command does not have a separate start character (|, >, <,
 * >>, "). Because of this, start for command is first word + each first word after the pipeline
 * Other correctness rules (sequence or number of various terminals) are moved to parser (if absolutely general) or
 * the appropriate command.
 * UnrecognizedTokenException is the consequence of unrecognized start sign. Each token's responsibility is to determine
 * whether the signs within the token are valid.
 */
class Lexer(val input: String) {
    var pos = 0
    private var commandTokenNext = true
    private val tokens = mutableListOf<Token>()

    private fun skipWhitespace() {
        while (this.pos < this.input.length && this.input[this.pos].isWhitespace()) this.pos++
    }

    fun tokenize(): MutableList<Token> {
        while (this.pos < this.input.length) {
            if (this.input[this.pos].isWhitespace()) {
                this.pos++
            } else if (this.commandTokenNext) {
                this.tokens.add(CommandToken(this))
                this.commandTokenNext = false
            } else if (this.input[this.pos] == '"') {
                this.tokens.add(QuotedToken(this))
            } else if (this.input[this.pos] == '|') {
                this.tokens.add(PipelineToken(this))
                this.commandTokenNext = true
            } else if (this.input[this.pos] == '-') {
                this.tokens.add(OptionToken(this))
            } else if (this.input[this.pos] == '>') {
                this.pos++
                if (this.pos < this.input.length && this.input[this.pos] == '>') {
                    this.pos++
                    this.skipWhitespace()
                    this.tokens.add(AppendOutputToken(this))
                } else {
                    this.skipWhitespace()
                    this.tokens.add(OutputToken(this))
                }
            } else if (this.input[this.pos] == '<') {
                this.pos++
                this.skipWhitespace()
                this.tokens.add(InputToken(this))
            } else if (this.input[this.pos].isLetter()) {
                this.tokens.add(NonQuotedToken(this))
            } else {
                throw UnrecognizedTokenException(this.input, this.pos)
            }
        }
        return this.tokens
    }

}