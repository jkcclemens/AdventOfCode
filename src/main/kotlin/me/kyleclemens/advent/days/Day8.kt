package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import kotlin.text.Regex

@UsesData(day = 8)
open class Day8 : Solution {

    private fun produceFirstAnswer(): Int {
        val literals = this.splitData.map { it.length }.sum()
        val memory = this.splitData
            .map {
                it.substring(1, it.length - 1).replace("\\\\", "\\").replace("\\\"", "\"")
            }
            .map { string ->
                Regex("\\\\x([a-fA-F0-9]{2})").findAll(string)
                    .map { it.groups[1] }
                    .filterNotNull()
                    .map { it.value to Integer.parseInt(it.value, 16).toChar() }
                    .fold(string) { str, pair -> str.replace("\\x${pair.first}", pair.second.toString()) }
            }
            .map { it.length }
            .sum()
        return literals - memory
    }

    private fun produceSecondAnswer(): Int {
        val literals = this.splitData.map { it.length }.sum()
        val escaped = this.splitData.map { it.replace("\\", "\\\\").replace("\"", "\\\"").length + 2 }.sum()
        return escaped - literals
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}

