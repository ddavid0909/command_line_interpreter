package commands.options.word_count

class WStrategy : Strategy() {
    override fun count(input: String): Int {
        return input.split("\\s+".toRegex()).size
    }
}