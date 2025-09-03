package exceptions.tokens

class NonQuotedTokenException(input: String, pos: Int) :
    TokenException(input, pos, "Token not supported in non-quoted token")
