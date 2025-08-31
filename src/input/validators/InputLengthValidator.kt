package input.validators

/**
 * Input Length Validator checks that the input does not exceed a defined length line limit.
 */
class InputLengthValidator(private val lineLimit: Int = 256) : InputValidator() {
    override fun check(input: String): String {
        var newInput : String = input
        if (input.length > this.lineLimit) {
            newInput = input.substring(0, lineLimit)
            System.err.println("Input '$input' too long. Truncated to '$newInput'")
        }
        return newInput
    }
}