package exceptions.tokens

class CommandTokenException(input: String, pos : Int) : TokenException(input, pos, "Token not supported in command name")