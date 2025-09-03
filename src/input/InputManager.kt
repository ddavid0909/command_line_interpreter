package input

import input.validators.InputLengthValidator
import input.validators.InputValidator
import lexer.Lexer
import parser.Parser

/**
 * Input Manager manages communication with the standard input
 * and performs all the validation checks that are registered to it.
 */
object InputManager {
    private var startSign: String = "$"
    private val lineLimit: Int = 256
    private var input: String = ""
    private val validators = mutableListOf<InputValidator>(InputLengthValidator(lineLimit))
    fun acceptInput() {
        print("$startSign ")
        val currentInput = readlnOrNull() ?: return
        this.input = currentInput

        for (validator in validators) {
            this.input = validator.check(this.input)
        }

        val lexer = Lexer(this.input)
        val tokens = lexer.tokenize()

        val parser = Parser(tokens)
        val command = parser.parse()
        command.invoke()

    }

    fun setStartSign(newStartSign: String) {
        this.startSign = newStartSign
    }

    fun addInputValidator(validator: InputValidator) {
        this.validators.add(validator)
    }


}