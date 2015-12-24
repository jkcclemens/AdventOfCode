package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 8)
open class Day8 : Solution {

    private fun produceFirstAnswer(): Int {
        // Sum the lengths of each line as a literal
        val literals = this.splitData.map { it.length }.sum()
        val memory = this.splitData
            // Map each line to its value when the outer quotes are removed and escapes are processed
            .map {
                it.substring(1, it.length - 1).replace("\\\\", "\\").replace("\\\"", "\"")
            }
            // Then, handle hexadecimal escapes
            .map { string ->
                Regex("\\\\x([a-fA-F0-9]{2})").findAll(string)
                    .map { it.groups[1] }
                    .filterNotNull()
                    .map { it.value to Integer.parseInt(it.value, 16).toChar() }
                    .fold(string) { str, pair -> str.replace("\\x${pair.first}", pair.second.toString()) }
            }
            // Then, map each line to its length, which is now its memory size
            .map { it.length }
            // Sum the memory sizes
            .sum()
        // Subtract the literal size from the memory size
        return literals - memory
    }

    private fun produceSecondAnswer(): Int {
        // Get the literal size again
        val literals = this.splitData.map { it.length }.sum()
        // Escape the literal, add two to its length for the outside quotes, then sum the length
        val escaped = this.splitData.map { it.replace("\\", "\\\\").replace("\"", "\\\"").length + 2 }.sum()
        // Subtract the escaped size from the literal size
        return escaped - literals
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}

