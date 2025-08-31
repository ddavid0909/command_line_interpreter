package exceptions.tokens

class AppendOutputTokenException(input: String, pos : Int) : TokenException(input, pos, "Token not supported in append output file name")