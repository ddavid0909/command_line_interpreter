import input.InputManager

fun main() {
    while (true) {
        try {
            val inputManager = InputManager
            inputManager.acceptInput()
        } catch (e: Exception) {
            println("ERROR: " + e.message)
        }
    }
}