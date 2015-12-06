package me.kyleclemens.advent.days

import me.kyleclemens.advent.helpers.Solution
import me.kyleclemens.advent.helpers.UsesData
import java.security.MessageDigest
import kotlin.text.Regex

@UsesData(day = 4)
open class Day4 : Solution {

    /**
     * Find the number necessary to append to the key to generate an MD5 hash starting with [num] zeroes.
     */
    private fun findNumberWithZeroes(num: Int): Int {
        // Make a string of zeroes equal to the length of num
        val goal = "0".repeat(num)
        // Get the key to append to
        val key = this.data.replace(Regex("\r?\n"), "")
        // Start at -1
        var i = -1
        while (true) {
            // Increment i, get the MD5, take the necessary number of bytes
            val bytes = MessageDigest.getInstance("MD5").digest("$key${++i}".toByteArray()).take(Math.ceil(num / 2.0).toInt())
            // Turn the bytes into a hex string and substring it to num length
            val result = bytes.map { Integer.toHexString(it.toInt()).padStart(2, '0') }.joinToString("").substring(0, num)
            if (result == goal) {
                // If the result is equal to our goal, return the number
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
