package commands.options.word_count

class CStrategy : Strategy() {
    override fun count(input: String): Int {
        return input.count { !it.isWhitespace() }
    }
}