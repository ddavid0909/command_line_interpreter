package commands.output

class StandardCommandOutput : CommandOutput() {
    override fun output(result: String) {
        println(result)
    }
}