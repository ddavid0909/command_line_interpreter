package lexer

import exceptions.tokens.*

sealed class Token(val value: String)

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
}

class QuotedToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this(
        buildString{
            lexer.pos++
            while (lexer.pos < lexer.input.length && lexer.input[lexer.pos] != '"') {
                append(lexer.input[lexer.pos])
                lexer.pos++
            }
            lexer.pos++
        }
    )
}
class InputToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this (
        buildString {
            lexer.pos++
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
}
class OutputToken(value: String): Token(value) {
    constructor(lexer: Lexer) : this (
        buildString {
            lexer.pos++
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
}

class AppendOutputToken(value:String): Token(value) {
    constructor(lexer: Lexer) : this (
        buildString {
            lexer.pos += 2
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
}
class PipelineToken(value: String) : Token(value) {
    constructor(lexer: Lexer) : this("|") {
        lexer.pos++;
    }
}
class OptionToken(value:String) : Token(value) {
    constructor(lexer: Lexer) : this (
        buildString {
            lexer.pos++
            while(lexer.pos < lexer.input.length && !lexer.input[lexer.pos].isWhitespace()) {
                val nextCharacter = lexer.input[lexer.pos]
                if (!nextCharacter.isLetter()) {
                    throw OptionTokenException(lexer.input, lexer.pos)
                }
                append(nextCharacter)
                lexer.pos++
            }
        }
    )
}

class NonQuotedToken(value:String) : Token(value) {
    constructor(lexer:Lexer) : this (
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

}
