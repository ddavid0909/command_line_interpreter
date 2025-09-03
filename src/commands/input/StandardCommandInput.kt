package commands.input

class StandardCommandInput : CommandInput() {
    override fun getArgument(): String {
        val builder = StringBuilder()
        while (true) {
            val line = readlnOrNull() ?: return builder.toString()
            builder.append("$line \n")
        }
    }

}