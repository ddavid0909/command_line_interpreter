package commands.options.head

class NStrategy : Strategy() {
    private var n: Int = 0
    override fun output(input: String) {
        TODO("Not yet implemented")
    }

    fun setN(N: Int) {
       this.n = N
    }
}