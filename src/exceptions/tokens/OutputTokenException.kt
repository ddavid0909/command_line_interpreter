package exceptions.tokens

class OutputTokenException(input: String, pos : Int) : TokenException(input, pos, "Token not supported in output file name")