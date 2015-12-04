package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import java.security.MessageDigest
import kotlin.text.Regex

@UsesData(day = 4)
open class Day4 : Solution {

    private fun findNumberWithZeroes(num: Int): Int {
        val goal = "".padEnd(num, '0')
        val key = this.getData().replace(Regex("\r?\n"), "")
        var i = -1
        while (true) {
            val firstThree = MessageDigest.getInstance("MD5").digest("$key${++i}".toByteArray()).take(Math.ceil(num / 2.0).toInt())
            val firstFive = firstThree.map { Integer.toHexString(it.toInt()).padStart(2, '0') }.joinToString("").substring(0, num)
            if (firstFive == goal) {
                return i
            }
        }
    }

    private fun produceFirstAnswer(): Int {
        return this.findNumberWithZeroes(5)
    }

    private fun produceSecondAnswer(): Int {
        return this.findNumberWithZeroes(6)
    }

    override val answers: Pair<Int, Int>
        get() = this.produceFirstAnswer() to this.produceSecondAnswer()
}
