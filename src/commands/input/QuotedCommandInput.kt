package commands.input

class QuotedCommandInput(private val input: String) : CommandInput() {
    override fun getArgument(): String {
        return this.input
    }

}