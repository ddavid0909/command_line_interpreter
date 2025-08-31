package input.validators

/**
 * Input Validator performs checks on the given input string and returns the transformed string.
 */
abstract class InputValidator {
    abstract fun check(input:String) : String
}