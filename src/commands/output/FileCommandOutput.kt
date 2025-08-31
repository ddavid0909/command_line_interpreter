package commands.output

import java.io.File

class FileCommandOutput(private val fileName: String) : CommandOutput() {
    override fun output(result: String) {
        File(fileName).writeText(result)
    }
}