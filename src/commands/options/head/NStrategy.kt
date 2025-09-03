package commands.options.head

import exceptions.syntax.BadOptionParameterException

class NStrategy : Strategy() {
    private var n: Int = 0
    override fun output(input: String): String {
        val builder = StringBuilder()
        var counter = n
        for (c in input) {
            if (c == '\n') counter--
            if (counter == 0) break
            builder.append(c)
        }
        return builder.toString()
    }

    override fun set(input: String) {
        try {
            this.n = input.substring(1, input.length).toInt()
            if (input.length > 6) throw BadOptionParameterException("Option -n in head command must take at most 5 digit numbers")
        } catch (e: NumberFormatException) {
            throw BadOptionParameterException("Option -n in head command must have a number connected to it")
        }
    }
}