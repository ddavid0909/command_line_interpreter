package lexer

import exceptions.tokens.*

sealed class Token(val value: String) {
    abstract fun getKind(): String
}

class CommandToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            while (lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetter()) {
                    throw CommandTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )

    override fun getKind(): String {
        return "Command"
    }
}

class QuotedToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            lexer.pos++
            while (lexer.pos < lexer.input.length && lexer.input[lexer.pos] != '"') {
                append(lexer.input[lexer.pos])
                lexer.pos++
            }
            lexer.pos++
        }
    )

    override fun getKind(): String {
        return "Quoted"
    }
}

class InputToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            while (lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetter() && nextCharacter != '.') {
                    throw InputTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )

    override fun getKind(): String {
        return "Input"
    }
}

class OutputToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            while (lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetter() && nextCharacter != '.') {
                    throw OutputTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )

    override fun getKind(): String {
        return "Output"
    }
}

class AppendOutputToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            while (lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetter() && nextCharacter != '.') {
                    throw AppendOutputTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )

    override fun getKind(): String {
        return "Append Output"
    }
}

class PipelineToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this("|") {
        lexer.pos++
    }

    override fun getKind(): String {
        return "Pipeline"
    }
}

class OptionToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            lexer.pos++
            while (lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetterOrDigit()) {
                    throw OptionTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )

    override fun getKind(): String {
        return "Option"
    }
}

class NonQuotedToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString {
            while (lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetter() && nextCharacter != '.') {
                    throw NonQuotedTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )

    override fun getKind(): String {
        return "NonQuoted"
    }

}
