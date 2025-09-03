package exceptions.tokens

class InputTokenException(input: String, pos: Int) :
    TokenException(input, pos, "Token not supported in input file name")