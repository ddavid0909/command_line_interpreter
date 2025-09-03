package exceptions

class UnknownCommandException(commandName: String) : Exception("Command $commandName is not implemented")