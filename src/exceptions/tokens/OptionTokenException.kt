package exceptions.tokens

class OptionTokenException(input: String, pos: Int) : TokenException(input, pos, "Token not supported in option")