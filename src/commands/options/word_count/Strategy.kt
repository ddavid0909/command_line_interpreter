package commands.options.word_count

abstract class Strategy {
    abstract fun count(input:String) : Int
}