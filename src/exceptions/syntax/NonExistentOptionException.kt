package exceptions.syntax

class NonExistentOptionException(option: String?) : Exception("Option $option not supported for command")