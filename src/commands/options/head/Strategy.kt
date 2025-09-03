package commands.options.head

abstract class Strategy {
    abstract fun output(input: String): String
    abstract fun set(input: String)
}