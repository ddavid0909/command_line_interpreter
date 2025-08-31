package commands.input

import java.io.File

class FileCommandInput(private val input: String) : CommandInput() {
    override fun getArgument(): String {
        return File(input).readText()
    }

}