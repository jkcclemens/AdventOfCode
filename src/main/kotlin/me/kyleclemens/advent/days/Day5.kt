package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData

@UsesData(day = 5)
open class Day5 : Solution {

    private fun String.hasRepeats(): Boolean {
        for (c in this.withIndex()) {
            if (c.index == this.length - 1) return false
            if (c.value == this[c.index + 1]) return true
        }
        return false
    }

    private fun String.hasRepeatsWithInBetweener(): Boolean {
        for (c in this.withIndex()) {
            if (c.index == this.length - 2) return false
            if (c.value == this[c.index + 2]) return true
        }
        return false
    }

    private fun String.hasPairs(): Boolean {
        for (c in this.withIndex()) {
            if (c.index == this.length - 2) return false
            val pair = this.substring(c.index, c.index + 2)
            if (this.length - (this.replace(pair, "").length) > 2) return true
        }
        return false
    }

    private fun String.isNiceFirstMethod(): Boolean {
        if (this.contains("ab") || this.contains("cd") || this.contains("pq") || this.contains("xy")) return false
        if (!this.hasRepeats()) return false
        if (this.count { it in "aeiou" } < 3) return false
        return true
    }

    private fun String.isNiceSecondMethod(): Boolean {
        if (!this.hasRepeatsWithInBetweener()) return false
        if (!this.hasPairs()) return false
        return true
    }

    private fun produceFirstAnswer(): Int {
        return this.splitData.count { it.isNiceFirstMethod() }
    }

    private fun produceSecondAnswer(): Int {
        return this.splitData.count { it.isNiceSecondMethod() }
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
