package parser

sealed class Terminal(val value: String)
class InputTerminal(value:String): Terminal(value)
class OutputTerminal(value:String): Terminal(value)
class LiteralTerminal(value:String): Terminal(value)
class AppendOutputTerminal(value:String): Terminal(value)
class OptionTerminal(value:String): Terminal(value)
