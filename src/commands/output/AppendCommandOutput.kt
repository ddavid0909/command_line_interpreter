package commands.output

import java.io.File

class AppendCommandOutput(private val fileName: String) : CommandOutput() {
    override fun output(result: String) {
        File(this.fileName).appendText(result)
    }
}