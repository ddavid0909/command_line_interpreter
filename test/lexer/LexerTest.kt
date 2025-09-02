package lexer

import org.junit.jupiter.api.Assertions.*
import java.io.File

class LexerTest {
    /**
     * File tests_positive has 2 lines per test
     * First line of each test is the actual command the lexer tokenizes
     * Second line is the kinds of resulting tokens in order.
     * These tests only verify token correctness, with the only 'semantic' rule being that command must be first token,
     * as well as the first after each pipeline token. The use of tokens is defined in terminals, and is outside the scope
     * of what lexer does.
     */
    @org.junit.jupiter.api.Test
    fun detectAllTokensPositive() {
        data class Record(
            val text: String,
            val items: List<String>
        )

        fun readRecords(filePath: String): List<Record> {
            val lines = File(filePath).readLines()
            val records = mutableListOf<Record>()

            for (i in lines.indices step 3) {
                if (i + 1 < lines.size) {
                    val text = lines[i]
                    val items = lines[i + 1].split(",").map { it.trim() }
                    records.add(Record(text, items))
                }
            }
            return records
        }
        val values = readRecords("test/lexer/tests_positive")
        for (value in values) {
            val lexer = Lexer(value.text)
            val tokens = lexer.tokenize()
            for (token in tokens.withIndex()) {
                assertEquals(token.value.getKind(), value.items[token.index])
            }
        }
    }

    /**
     * Covers many negative cases. Explanation to each case is within the comment.
     * Lexer detects token with the start sign. If sign resolves to no token - UnrecognizedTokenException is thrown
     * Otherwise, a token itself determines whether a character can exist within it. If not - it throws the corresponding
     * exception
     */
    @org.junit.jupiter.api.Test
    fun detectAllTokensNegative() {
        data class Record(
            val text: String,
            val expectedException: Class<out Throwable>
        )

        fun readRecords(filePath: String): List<Record> {
            val records = mutableListOf<Record>()
            val lines = File(filePath).readLines()

            for (i in lines.indices step 4) {
                if (i + 1 < lines.size) {
                    val text = lines[i]
                    val exceptionClassName = lines[i + 1]
                    val exceptionClass = Class.forName(exceptionClassName).asSubclass(Throwable::class.java)
                    records.add(Record(text, exceptionClass))
                }
            }

            return records
        }

        val values = readRecords("test/lexer/tests_negative")
        for (value in values) {
            val lexer = Lexer(value.text)
            assertThrows(value.expectedException) {
                lexer.tokenize()
            }
        }
    }
}