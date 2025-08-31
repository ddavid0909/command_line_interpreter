package exceptions.tokens

open class TokenException (
    val input: String,
    private val pos: Int,
    message: String
) : Exception(message) {

    override val message: String
        get() {
            val pointer = " ".repeat(pos) + "^"
            return buildString {
                appendLine(super.message)
                appendLine(input)
                append(pointer)
            }
        }
}