package exceptions.tokens

class UnrecognizedTokenException(input: String, pos : Int) : TokenException(input, pos, "Token not supported")