package input

import commands.CommandRegistry
import commands.*
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

        CommandRegistry.registerCommand("echo", {EchoCommand()})
        CommandRegistry.registerCommand("date", {DateCommand()})
        CommandRegistry.registerCommand("time", {TimeCommand()})
        CommandRegistry.registerCommand("prompt", {PromptCommand()})
        CommandRegistry.registerCommand("touch", {TouchCommand()})
        CommandRegistry.registerCommand("truncate", {TruncateCommand()})
        CommandRegistry.registerCommand("rm", {RemoveCommand()})
        CommandRegistry.registerCommand("wc", {WordCountCommand()})

        val lexer = Lexer(this.input)
        val tokens = lexer.tokenize()
        //lexer.printList()

        val parser = Parser(tokens)
        val command = parser.parse()
        command.invoke()
       // print(parser.parse())

    }

    fun setStartSign(newStartSign: String) {
        this.startSign = newStartSign
    }

    fun addInputValidator(validator: InputValidator) {
        this.validators.add(validator)
    }


}