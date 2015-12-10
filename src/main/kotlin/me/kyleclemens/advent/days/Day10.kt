package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 10)
open class Day10 : Solution {

    private fun lookAndSay(input: String): String {
        var lastIndex = 0
        var result = StringBuilder()
        var last: Char = input[0]
        for (item in input.withIndex()) {
            if (item.index == 0) continue
            if (item.value != last) {
                result.append("${item.index - lastIndex}$last")
                lastIndex = item.index
            }
            last = item.value
        }
        val lastDistance = input.length - lastIndex
        if (lastDistance != 0) {
            result.append("$lastDistance${input[input.length - 1]}")
        }
        return result.toString()
    }

    private fun produceFirstAnswer(): Int {
        var last = this.splitData[0]
        for (i in 1..40) {
            last = this.lookAndSay(last)
        }
        return last.length
    }

    private fun produceSecondAnswer(): Int {
        var last = this.splitData[0]
        for (i in 1..50) {
            last = this.lookAndSay(last)
        }
        return last.length
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}

